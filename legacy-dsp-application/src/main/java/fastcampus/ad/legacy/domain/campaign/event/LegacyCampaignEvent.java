package fastcampus.ad.legacy.domain.campaign.event;

import fastcampus.ad.legacy.domain.AggregateType;
import fastcampus.ad.legacy.domain.DomainEvent;
import fastcampus.ad.legacy.domain.campaign.LegacyCampaign;
import java.time.LocalDateTime;

public abstract class LegacyCampaignEvent implements DomainEvent {

  protected LegacyCampaign legacyCampaign;

  public LegacyCampaignEvent(LegacyCampaign legacyCampaign) {
    this.legacyCampaign = legacyCampaign;
  }

  @Override
  public AggregateType aggregateType() {
    return AggregateType.CAMPAIGN;
  }

  @Override
  public Long aggregateId() {
    return legacyCampaign.getId();
  }

  @Override
  public Long ownerId() {
    return legacyCampaign.getUserId();
  }

  @Override
  public abstract LocalDateTime occurredOn();

}
