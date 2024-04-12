package fastcampus.ad.migration.domain.migration.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MigrationUsersTest {

  @Test
  void statusStatistics() {
    MigrationUsers users = MigrationUsers.of(
        List.of(MigrationUser.agreed(1L), MigrationUser.agreed(2L), progressed(3L, 1),
            progressed(4L, 2)));

    assertThat(users.statusStatistics()).isEqualTo(
        Map.of(MigrationUserStatus.AGREED, 2L, MigrationUserStatus.USER_FINISHED, 1L,
            MigrationUserStatus.ADGROUP_FINISHED, 1L));
  }

  private MigrationUser progressed(Long userId, int num) {
    MigrationUser user = MigrationUser.agreed(userId);
    for (int i = 0; i < num; i++) {
      user.progressMigration();
    }
    return user;
  }
}