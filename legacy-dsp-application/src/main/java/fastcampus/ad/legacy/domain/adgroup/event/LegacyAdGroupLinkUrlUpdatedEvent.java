package fastcampus.ad.legacy.domain.adgroup.event;

import fastcampus.ad.legacy.domain.adgroup.LegacyAdGroup;
import java.time.LocalDateTime;

public class LegacyAdGroupLinkUrlUpdatedEvent extends LegacyAdGroupEvent {

  public LegacyAdGroupLinkUrlUpdatedEvent(LegacyAdGroup legacyAdGroup) {
    super(legacyAdGroup);
  }

  @Override
  public LocalDateTime occurredOn() {
    return legacyAdGroup.getUpdatedAt();
  }

}
