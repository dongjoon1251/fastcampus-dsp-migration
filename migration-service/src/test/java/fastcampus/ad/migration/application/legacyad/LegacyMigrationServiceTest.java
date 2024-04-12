package fastcampus.ad.migration.application.legacyad;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fastcampus.ad.migration.domain.legacyad.DeletableEntity;
import fastcampus.ad.migration.domain.recentad.MigratedEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.repository.CrudRepository;

class LegacyMigrationServiceTest {

  @Mock
  CrudRepository<DeletableEntity, Long> legacyRepository;
  @Mock
  CrudRepository<MigratedEntity, Long> recentRepository;
  @Mock
  LegacyConverter<DeletableEntity, MigratedEntity> converter;

  LegacyMigrationService<?, ?> service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    service = new LegacyMigrationService<>(
        legacyRepository, recentRepository, converter) {
      @Override
      public boolean migrate(Long id) {
        return super.migrate(id);
      }
    };
  }

  @Test
  void 레거시_도메인_조회시_데이터가_없으면_마이그레이션_실패() {
    when(legacyRepository.findById(1L)).thenReturn(Optional.empty());

    boolean result = service.migrate(1L);

    assertThat(result).isFalse();
  }

  @Test
  void 레거시_도메인이_삭제되었고_마이그레이션된_적_있으면_삭제() {
    삭제된_레거시가_조회됨(1L);
    when(recentRepository.findById(1L)).thenReturn(
        Optional.of(() -> LocalDateTime.now().minusHours(1)));

    boolean result = service.migrate(1L);

    assertAll(() -> assertThat(result).isTrue(),
        () -> verify(recentRepository, times(1)).delete(any()));
  }

  private void 삭제된_레거시가_조회됨(Long id) {
    when(legacyRepository.findById(id)).thenReturn(Optional.of(new DeletableEntity() {
      @Override
      public LocalDateTime getDeletedAt() {
        return LocalDateTime.now();
      }

      @Override
      public Long getId() {
        return id;
      }
    }));
  }

  @Test
  void 레거시_도메인이_삭제되지_않았으면_마이그레이션() {
    삭제되지_않은_레거시가_조회됨(1L);
    when(converter.convert(any())).thenReturn(() -> LocalDateTime.now().minusHours(1));

    boolean result = service.migrate(1L);

    assertAll(() -> assertThat(result).isTrue(),
        () -> verify(recentRepository, times(1)).save(any()));
  }

  private void 삭제되지_않은_레거시가_조회됨(Long id) {
    when(legacyRepository.findById(id)).thenReturn(Optional.of(new DeletableEntity() {
      @Override
      public LocalDateTime getDeletedAt() {
        return null;
      }

      @Override
      public Long getId() {
        return id;
      }
    }));
  }

  @Test
  void 마이그레이션_중에_RuntimeException발생하면_실패() {
    service = errorMigrationService();

    boolean result = service.migrate(1L);

    assertThat(result).isFalse();
  }

  @NotNull
  private LegacyMigrationService<DeletableEntity, MigratedEntity> errorMigrationService() {
    return new LegacyMigrationService<>(
        legacyRepository, recentRepository, converter) {
      @Override
      public void migrate(DeletableEntity deletableEntity) {
        throw new RuntimeException();
      }
    };
  }

  @Test
  void 조회된_레거시를_다른_로직으로_마이그레이션할_수_있음() {
    service = customMigrationService();
    삭제되지_않은_레거시가_조회됨(1L);

    boolean result = service.migrate(1L);

    assertAll(() -> assertThat(result).isTrue(),
        () -> verify(recentRepository, times(0)).findById(any()),
        () -> verify(converter, times(0)).convert(any()),
        () -> verify(recentRepository, times(0)).save(any())
    );
  }

  private LegacyMigrationService<DeletableEntity, MigratedEntity> customMigrationService() {
    return new LegacyMigrationService<>(
        legacyRepository, recentRepository, converter) {
      @Override
      public void migrate(DeletableEntity deletableEntity) {

      }
    };
  }
}