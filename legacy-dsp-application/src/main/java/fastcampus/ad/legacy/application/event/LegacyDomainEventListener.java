package fastcampus.ad.legacy.application.event;

import fastcampus.ad.legacy.domain.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class LegacyDomainEventListener {

  private static final String OUTPUT_BINDING = "legacy-rabbit-out";
  private final StreamBridge streamBridge;

  @TransactionalEventListener
  public void handleEvent(DomainEvent event) {
    streamBridge.send(OUTPUT_BINDING, LegacyDomainMessage.from(event));
  }

}
