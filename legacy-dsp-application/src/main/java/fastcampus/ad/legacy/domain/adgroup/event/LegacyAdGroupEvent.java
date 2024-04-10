package fastcampus.ad.legacy.domain.adgroup.event;

import fastcampus.ad.legacy.domain.AggregateType;
import fastcampus.ad.legacy.domain.DomainEvent;
import fastcampus.ad.legacy.domain.adgroup.LegacyAdGroup;
import java.time.LocalDateTime;

public abstract class LegacyAdGroupEvent implements DomainEvent {

  protected LegacyAdGroup legacyAdGroup;

  public LegacyAdGroupEvent(LegacyAdGroup legacyAdGroup) {
    this.legacyAdGroup = legacyAdGroup;
  }

  @Override
  public AggregateType aggregateType() {
    return AggregateType.ADGROUP;
  }

  @Override
  public Long aggregateId() {
    return legacyAdGroup.getId();
  }

  @Override
  public Long ownerId() {
    return legacyAdGroup.getUserId();
  }

  @Override
  public abstract LocalDateTime occurredOn();

}
