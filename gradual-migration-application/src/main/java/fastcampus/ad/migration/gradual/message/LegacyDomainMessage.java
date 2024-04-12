package fastcampus.ad.migration.gradual.message;

import fastcampus.ad.migration.domain.AggregateType;
import java.time.LocalDateTime;

public record LegacyDomainMessage(AggregateType aggregateType, Long aggregateId, Long ownerId,
                                  LocalDateTime occurredOn) {

}
