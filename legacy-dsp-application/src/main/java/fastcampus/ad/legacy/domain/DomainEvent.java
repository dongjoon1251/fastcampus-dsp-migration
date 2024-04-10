package fastcampus.ad.legacy.domain;

import java.time.LocalDateTime;

public interface DomainEvent {

  AggregateType aggregateType();

  Long aggregateId();

  LocalDateTime occurredOn();

  Long ownerId();
}
