package fastcampus.ad.migration.batch.application.message;

import fastcampus.ad.migration.application.user.MigrationUserService;
import fastcampus.ad.migration.application.user.StartMigrationFailedException;
import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationMessageProcessor {

  private final MigrationUserService migrationUserService;

  public void progressMigration(MigrationUserStatus status, Long userId) {
    switch (status) {
      case AGREED -> startMigration(userId);
      case USER_FINISHED, ADGROUP_FINISHED, KEYWORD_FINISHED ->
          migrationUserService.progressMigration(userId);
    }
  }

  private void startMigration(Long userId) {
    try {
      migrationUserService.startMigration(userId);
    } catch (StartMigrationFailedException e) {
      log.error("start migration failed", e);
    }
  }

}
