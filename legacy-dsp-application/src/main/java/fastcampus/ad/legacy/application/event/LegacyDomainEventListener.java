package fastcampus.ad.legacy.application.event;

import fastcampus.ad.legacy.domain.DomainEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class LegacyDomainEventListener {


  @TransactionalEventListener
  public void handleEvent(DomainEvent event) {
  }

}
