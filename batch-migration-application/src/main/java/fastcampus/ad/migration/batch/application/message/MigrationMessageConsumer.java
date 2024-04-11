package fastcampus.ad.migration.batch.application.message;

import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MigrationMessageConsumer {

  @Bean
  public Consumer<MigrationUserMessage> migrationUserConsumer() {
    return message -> log.info(message.toString());
  }

}
