package fastcampus.ad.legacy.domain.keyword.event;

import fastcampus.ad.legacy.domain.keyword.LegacyKeyword;
import java.time.LocalDateTime;

public class LegacyKeywordCreatedEvent extends LegacyKeywordEvent {

  public LegacyKeywordCreatedEvent(LegacyKeyword legacyKeyword) {
    super(legacyKeyword);
  }

  @Override
  public LocalDateTime occurredOn() {
    return legacyKeyword.getCreatedAt();
  }

}
