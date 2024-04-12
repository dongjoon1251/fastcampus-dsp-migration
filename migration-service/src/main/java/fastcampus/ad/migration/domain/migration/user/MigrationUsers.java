package fastcampus.ad.migration.domain.migration.user;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public record MigrationUsers(Iterable<MigrationUser> migrationUsers) {

  public static MigrationUsers of(Iterable<MigrationUser> migrationUsers) {
    return new MigrationUsers(migrationUsers);
  }

  public Map<MigrationUserStatus, Long> statusStatistics() {
    return StreamSupport.stream(migrationUsers.spliterator(), true)
        .collect(Collectors.groupingBy(MigrationUser::getStatus, Collectors.counting()));
  }
}
