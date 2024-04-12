package fastcampus.ad.migration.internal.api.migration;

import fastcampus.ad.migration.application.dispatcher.MigrationDispatcher;
import fastcampus.ad.migration.application.monitoring.MigrationMonitor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/migration/v1/")
public class MigrationController {

  private final MigrationDispatcher migrationDispatcher;
  private final MigrationMonitor migrationMonitor;

  @PutMapping("/retry")
  public MigrationRetryResp retryMigration(@RequestBody MigrationRetryRequest request) {
    boolean result = migrationDispatcher.dispatch(request.userId(), request.aggregateId(),
        request.aggregateType());
    return new MigrationRetryResp(result);
  }

  @GetMapping("/progress")
  public MigrationProgressResp getMigrationProgress() {
    return MigrationProgressResp.from(migrationMonitor.measure());
  }
}
