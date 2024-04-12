package fastcampus.ad.migration.domain.migration.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class MigrationUserTest {

  @Test
  void 다음_도메인_마이그레이션_진행() {
    MigrationUser user = MigrationUser.agreed(1L);

    LocalDateTime beforeUpdate = LocalDateTime.now();
    user.progressMigration();
    LocalDateTime afterUpdate = LocalDateTime.now();

    assertAll(() -> assertThat(user.getStatus()).isEqualTo(MigrationUserStatus.AGREED.next()),
        () -> assertThat(user.getUpdateAt()).isAfter(beforeUpdate).isBefore(afterUpdate),
        () -> assertThat(user.getPrevStatus()).isEqualTo(MigrationUserStatus.AGREED));
  }

  @Test
  void 재시도하면_상태를_RETRIED로_바꾸고_이전_상태를_저장() {
    MigrationUser user = MigrationUser.agreed(1L);
    user.progressMigration();

    user.retry();

    assertAll(() -> assertThat(user.getStatus()).isEqualTo(MigrationUserStatus.RETRIED),
        () -> assertThat(user.getPrevStatus()).isEqualTo(MigrationUserStatus.USER_FINISHED));
  }

  @Test
  void 재시도하고_다음_도메인_마이그레이션_진행할_때는_prevStatus의_다음으로_상태변경() {
    MigrationUser user = MigrationUser.agreed(1L);
    user.progressMigration();
    user.retry();

    user.progressMigration();

    assertAll(
        () -> assertThat(user.getStatus()).isEqualTo(MigrationUserStatus.USER_FINISHED.next()),
        () -> assertThat(user.getPrevStatus()).isEqualTo(MigrationUserStatus.USER_FINISHED));
  }
}