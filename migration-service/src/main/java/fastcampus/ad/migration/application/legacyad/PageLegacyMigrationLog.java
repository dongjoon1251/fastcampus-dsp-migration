package fastcampus.ad.migration.application.legacyad;

import fastcampus.ad.migration.domain.AggregateType;

public record PageLegacyMigrationLog(Long userId, AggregateType aggregateType, int pageNumber,
                                     int totalPages, Long totalCount, boolean isSuccess) {

  public static PageLegacyMigrationLog success(PageMigrationResult result,
      AggregateType aggregateType) {
    return new PageLegacyMigrationLog(result.userId(), aggregateType, result.pageNumber(),
        result.totalPages(), result.totalCount(), true);
  }

  public static PageLegacyMigrationLog fail(PageMigrationResult result,
      AggregateType aggregateType) {
    return new PageLegacyMigrationLog(result.userId(), aggregateType, result.pageNumber(),
        result.totalPages(), result.totalCount(), false);
  }
}
