package fastcampus.ad.migration.internal.api.migration;

import fastcampus.ad.migration.application.monitoring.MigrationMonitorMetrics;
import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;
import java.util.Map;

public record MigrationProgressResp(Map<MigrationUserStatus, Long> statusStatistics,
                                    Long adGroupMigratedCount, Long adGroupTotalCount,
                                    Long keywordMigratedCount, Long keywordTotalCount) {

  public static MigrationProgressResp from(MigrationMonitorMetrics metrics) {
    return new MigrationProgressResp(metrics.statusStatistics(), metrics.adGroupMigratedCount(),
        metrics.adGroupTotalCount(), metrics.keywordMigratedCount(), metrics.keywordTotalCount());
  }
}
