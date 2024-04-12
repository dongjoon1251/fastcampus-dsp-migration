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
}