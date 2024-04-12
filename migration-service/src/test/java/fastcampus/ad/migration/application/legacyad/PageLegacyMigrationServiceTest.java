package fastcampus.ad.migration.application.legacyad;

import static fastcampus.ad.migration.application.legacyad.PageLegacyMigrationService.PAGE_SIZE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import fastcampus.ad.migration.domain.legacyad.DeletableEntity;
import fastcampus.ad.migration.domain.legacyad.LegacyPageableRepository;
import fastcampus.ad.migration.domain.migration.PageMigration;
import fastcampus.ad.migration.domain.migration.PageMigrationRepository;
import fastcampus.ad.migration.domain.migration.PageMigrationTestable;
import fastcampus.ad.migration.domain.recentad.MigratedEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

class PageLegacyMigrationServiceTest {

  @Mock
  PageMigrationRepository<PageMigrationTestable> pageMigrationRepository;
  @Mock
  LegacyPageableRepository<DeletableEntity> legacyPageableRepository;
  @Mock
  LegacyMigrationService<DeletableEntity, MigratedEntity> legacyMigrationService;

  PageLegacyMigrationService<PageMigrationTestable, DeletableEntity, MigratedEntity> service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    service = new PageLegacyMigrationService<>(pageMigrationRepository,
        legacyPageableRepository, legacyMigrationService) {
      @Override
      protected PageMigrationTestable firstPageMigration(Long userId, boolean isSuccess,
          Page<DeletableEntity> legacies) {
        if (isSuccess) {
          return new PageMigrationTestable(userId, PageMigration.INIT_PAGE_NUMBER, PAGE_SIZE,
              legacies.getTotalElements());
        }
        return new PageMigrationTestable(userId, PageMigration.NOT_STARTED_PAGE_NUMBER, PAGE_SIZE,
            legacies.getTotalElements());
      }
    };
  }

  @Test
  void pageMigration시작이고_마이그레이션_성공이면_INIT_PAGE_NUMBER로_firstPageMigration을_저장() {
    when(pageMigrationRepository.findById(any())).thenReturn(Optional.empty());
    when(legacyPageableRepository.findAllByUserIdAndDeletedAtIsNullOrderById(any(),
        any())).thenReturn(new PageImpl<>(List.of(legacyTestable(1L))));
    when(legacyMigrationService.migrate(anyList())).thenReturn(true);
    when(pageMigrationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    var result = service.migrate(1L);

    assertThat(result).isEqualTo(
        new PageMigrationResult(1L, PageMigration.INIT_PAGE_NUMBER, 1, 1L, true));
  }

  @Test
  void pageMigration시작이고_마이그레이션_실패이면_NOT_STARTED_PAGE_NUMBER로_firstPageMigration을_저장() {
    when(pageMigrationRepository.findById(any())).thenReturn(Optional.empty());
    when(legacyPageableRepository.findAllByUserIdAndDeletedAtIsNullOrderById(any(),
        any())).thenReturn(new PageImpl<>(List.of(legacyTestable(1L))));
    when(legacyMigrationService.migrate(anyList())).thenReturn(false);
    when(pageMigrationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    var result = service.migrate(1L);

    assertThat(result).isEqualTo(
        new PageMigrationResult(1L, PageMigration.NOT_STARTED_PAGE_NUMBER, 1, 1L, false));
  }

  @Test
  void pageMigration_두번째_페이지이고_마이그레이션_성공이면_다음_페이지로_PageMigration을_저장() {
    when(pageMigrationRepository.findById(any())).thenReturn(
        Optional.of(new PageMigrationTestable(1L, PageMigration.INIT_PAGE_NUMBER, PAGE_SIZE,
            11L)));
    when(legacyPageableRepository.findAllByUserIdAndDeletedAtIsNullOrderById(any(),
        any())).thenReturn(new PageImpl<>(List.of(legacyTestable(1L)),
        PageRequest.of(PageMigration.INIT_PAGE_NUMBER, PAGE_SIZE), 11L));
    when(legacyMigrationService.migrate(anyList())).thenReturn(true);
    when(pageMigrationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    var result = service.migrate(1L);

    assertThat(result).isEqualTo(
        new PageMigrationResult(1L, PageMigration.INIT_PAGE_NUMBER + PageMigration.PAGE_INCREMENT,
            2, 11L, true));
  }

  @Test
  void pageMigration_두번쨰_페이지이고_마이그레이션_실패이면_원래_페이지로_PageMigration을_저장() {
    when(pageMigrationRepository.findById(any())).thenReturn(
        Optional.of(new PageMigrationTestable(1L, PageMigration.INIT_PAGE_NUMBER, PAGE_SIZE,
            11L)));
    when(legacyPageableRepository.findAllByUserIdAndDeletedAtIsNullOrderById(any(),
        any())).thenReturn(new PageImpl<>(List.of(legacyTestable(1L)),
        PageRequest.of(PageMigration.INIT_PAGE_NUMBER, PAGE_SIZE), 11L));
    when(legacyMigrationService.migrate(anyList())).thenReturn(false);
    when(pageMigrationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    var result = service.migrate(1L);

    assertThat(result).isEqualTo(
        new PageMigrationResult(1L, PageMigration.INIT_PAGE_NUMBER, 2, 11L, false));
  }


  private DeletableEntity legacyTestable(Long id) {
    return new DeletableEntity() {
      @Override
      public LocalDateTime getDeletedAt() {
        return null;
      }

      @Override
      public Long getId() {
        return id;
      }
    };
  }
}