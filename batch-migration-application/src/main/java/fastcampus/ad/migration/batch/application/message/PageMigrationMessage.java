package fastcampus.ad.migration.batch.application.message;

import fastcampus.ad.migration.domain.AggregateType;

public record PageMigrationMessage(Long userId, AggregateType aggregateType, boolean isFinished) {

}
