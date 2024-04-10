package fastcampus.ad.migration.gradual.message;

import fastcampus.ad.migration.gradual.application.dispatcher.MigrationDispatcher;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LegacyDomainMessageHandler {

  private final MigrationDispatcher migrationDispatcher;

  @Bean
  public Consumer<LegacyDomainMessage> legacyConsumer() {
    return message -> migrationDispatcher.dispatch(message.aggregateId(), message.aggregateType());
  }

}
