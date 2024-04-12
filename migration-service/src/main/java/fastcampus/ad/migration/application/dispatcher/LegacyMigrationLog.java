package fastcampus.ad.migration.application.dispatcher;


import fastcampus.ad.migration.domain.AggregateType;

public record LegacyMigrationLog(boolean isSuccess, AggregateType aggregateType, Long aggregateId) {

  public static LegacyMigrationLog success(AggregateType aggregateType, Long aggregateId) {
    return new LegacyMigrationLog(true, aggregateType, aggregateId);
  }

  public static LegacyMigrationLog fail(AggregateType aggregateType, Long aggregateId) {
    return new LegacyMigrationLog(false, aggregateType, aggregateId);
  }
}
