package fastcampus.ad.migration.domain.migration;

import fastcampus.ad.migration.domain.AggregateType;

public record PageMigrationEvent(AggregateType aggregateType, Long userId, boolean isFinished) {

}
