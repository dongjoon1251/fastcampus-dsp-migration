package fastcampus.ad.migration.gradual.message;

import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LegacyDomainMessageHandler {

  @Bean
  public Consumer<LegacyDomainMessage> legacyConsumer() {
    return message -> log.info(message.toString());
  }

}
