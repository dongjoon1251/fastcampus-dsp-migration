package fastcampus.ad.migration.application.user;

import fastcampus.ad.migration.domain.migration.user.MigrationUser;
import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;
import java.time.LocalDateTime;

public record MigrationUserResult(Long id, MigrationUserStatus status, LocalDateTime agreedDate,
                                  LocalDateTime updateDate) {

  public static MigrationUserResult from(MigrationUser migrationUser) {
    return new MigrationUserResult(migrationUser.getId(), migrationUser.getStatus(),
        migrationUser.getAgreedAt(), migrationUser.getUpdateAt());
  }
}
