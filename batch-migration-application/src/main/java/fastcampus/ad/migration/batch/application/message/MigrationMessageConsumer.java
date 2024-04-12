package fastcampus.ad.migration.batch.application.message;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationMessageConsumer {

  private final MigrationMessageProcessor processor;

  @Bean
  public Consumer<MigrationUserMessage> migrationUserConsumer() {
    return message -> {
      processor.progressMigration(message.status(), message.userId());
      log.info("migration user consumer: {}", message);
    };
  }

  @Bean
  public Consumer<PageMigrationMessage> pageMigrationConsumer() {
    return message -> {
      processor.processPageMigration(message.userId(), message.aggregateType(),
          message.isFinished());
      log.info("page migration consumer: {}", message);
    };
  }
}
