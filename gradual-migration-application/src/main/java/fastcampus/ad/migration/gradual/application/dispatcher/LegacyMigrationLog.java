package fastcampus.ad.migration.gradual.application.dispatcher;


import fastcampus.ad.migration.gradual.domain.AggregateType;

public record LegacyMigrationLog(boolean isSuccess, AggregateType aggregateType, Long aggregateId) {

  public static LegacyMigrationLog success(AggregateType aggregateType, Long aggregateId) {
    return new LegacyMigrationLog(true, aggregateType, aggregateId);
  }

  public static LegacyMigrationLog fail(AggregateType aggregateType, Long aggregateId) {
    return new LegacyMigrationLog(false, aggregateType, aggregateId);
  }
}
