package fastcampus.ad.migration.internal.api.migration;

import fastcampus.ad.migration.domain.AggregateType;

public record MigrationRetryRequest(Long userId, Long aggregateId, AggregateType aggregateType) {

}
