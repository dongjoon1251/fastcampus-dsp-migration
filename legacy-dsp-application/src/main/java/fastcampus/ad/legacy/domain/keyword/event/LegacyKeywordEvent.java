package fastcampus.ad.legacy.domain.keyword.event;

import fastcampus.ad.legacy.domain.AggregateType;
import fastcampus.ad.legacy.domain.DomainEvent;
import fastcampus.ad.legacy.domain.keyword.LegacyKeyword;
import java.time.LocalDateTime;

public abstract class LegacyKeywordEvent implements DomainEvent {

  protected LegacyKeyword legacyKeyword;

  public LegacyKeywordEvent(LegacyKeyword legacyKeyword) {
    this.legacyKeyword = legacyKeyword;
  }

  @Override
  public AggregateType aggregateType() {
    return AggregateType.KEYWORD;
  }

  @Override
  public Long aggregateId() {
    return legacyKeyword.getId();
  }

  @Override
  public Long ownerId() {
    return legacyKeyword.getUserId();
  }

  @Override
  public abstract LocalDateTime occurredOn();

}
