package fastcampus.ad.legacy.domain.user.event;

import fastcampus.ad.legacy.domain.AggregateType;
import fastcampus.ad.legacy.domain.DomainEvent;
import fastcampus.ad.legacy.domain.user.LegacyUser;
import java.time.LocalDateTime;

public abstract class LegacyUserEvent implements DomainEvent {

  protected LegacyUser legacyUser;

  public LegacyUserEvent(LegacyUser legacyUser) {
    this.legacyUser = legacyUser;
  }

  @Override
  public AggregateType aggregateType() {
    return AggregateType.USER;
  }

  @Override
  public Long aggregateId() {
    return legacyUser.getId();
  }

  @Override
  public Long ownerId() {
    return legacyUser.getId();
  }

  @Override
  public abstract LocalDateTime occurredOn();

}
