package fastcampus.ad.legacy.domain.user.event;

import fastcampus.ad.legacy.domain.user.LegacyUser;
import java.time.LocalDateTime;

public class LegacyUserNameUpdatedEvent extends LegacyUserEvent {

  public LegacyUserNameUpdatedEvent(LegacyUser legacyUser) {
    super(legacyUser);
  }

  @Override
  public LocalDateTime occurredOn() {
    return legacyUser.getUpdatedAt();
  }

}
