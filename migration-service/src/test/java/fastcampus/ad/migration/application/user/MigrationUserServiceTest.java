package fastcampus.ad.migration.application.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import fastcampus.ad.migration.application.legacyad.user.LegacyUserMigrationService;
import fastcampus.ad.migration.domain.migration.user.MigrationUser;
import fastcampus.ad.migration.domain.migration.user.MigrationUserRepository;
import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MigrationUserServiceTest {

  @Mock
  MigrationUserRepository repository;
  @Mock
  LegacyUserMigrationService legacyUserMigrationService;

  @InjectMocks
  MigrationUserService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void agree() {
    when(repository.findById(1L)).thenReturn(Optional.empty());
    when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    MigrationUserResult result = service.agree(1L);

    assertAll(() -> assertThat(result.id()).isEqualTo(1L),
        () -> assertThat(result.status()).isEqualTo(MigrationUserStatus.AGREED),
        () -> assertThat(result.agreedDate()).isNotNull(),
        () -> assertThat(result.updateDate()).isNotNull());
  }

  @Test
  void 이미_마이그레이션에_동의했으면_에러() {
    when(repository.findById(1L)).thenReturn(Optional.of(MigrationUser.agreed(1L)));

    assertThatThrownBy(() -> service.agree(1L))
        .isInstanceOf(AlreadyAgreedException.class);
  }

  @Test
  void 존재하지_않으면_에러() {
    when(repository.findById(1L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.findById(1L))
        .isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  void 동의하지_않았으면_true() {
    when(repository.findById(1L)).thenReturn(Optional.empty());

    boolean result = service.isDisagreed(1L);

    assertThat(result).isTrue();
  }

  @Test
  void 동의했으면_false() {
    when(repository.findById(1L)).thenReturn(Optional.of(MigrationUser.agreed(1L)));

    boolean result = service.isDisagreed(1L);

    assertThat(result).isFalse();
  }

  @Test
  void 마이그레이션_시작하고_사용자_마이그레이션_성공하면_상태_업데이트() throws StartMigrationFailedException {
    when(legacyUserMigrationService.migrate(1L)).thenReturn(true);
    when(repository.findById(1L)).thenReturn(Optional.of(MigrationUser.agreed(1L)));
    when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    MigrationUserResult result = service.startMigration(1L);

    assertThat(result.status()).isEqualTo(MigrationUserStatus.USER_FINISHED);
  }

  @Test
  void 마이그레이션_시작하고_사용자_마이그레이션_실패하면_에러() {
    when(legacyUserMigrationService.migrate(1L)).thenReturn(false);

    assertThatThrownBy(() -> service.startMigration(1L))
        .isInstanceOf(StartMigrationFailedException.class);
  }

  @Test
  void 마이그레이션_진행하면_사용자_상태_업데이트() {
    when(repository.findById(1L)).thenReturn(Optional.of(MigrationUser.agreed(1L)));
    when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    MigrationUserResult result = service.progressMigration(1L);

    assertAll(() -> assertThat(result.status()).isEqualTo(MigrationUserStatus.USER_FINISHED),
        () -> assertThat(result.prevStatus()).isEqualTo(MigrationUserStatus.AGREED));
  }
}