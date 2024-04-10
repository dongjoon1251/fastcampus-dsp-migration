package fastcampus.ad.legacy.domain.campaign.event;

import fastcampus.ad.legacy.domain.campaign.LegacyCampaign;
import java.time.LocalDateTime;

public class LegacyCampaignCreatedEvent extends LegacyCampaignEvent {

  public LegacyCampaignCreatedEvent(LegacyCampaign legacyCampaign) {
    super(legacyCampaign);
  }

  @Override
  public LocalDateTime occurredOn() {
    return legacyCampaign.getCreatedAt();
  }

}
