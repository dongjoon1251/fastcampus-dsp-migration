package fastcampus.ad.migration.application.dispatcher;

import fastcampus.ad.migration.application.legacyad.PageLegacyMigrationLog;
import fastcampus.ad.migration.application.legacyad.PageLegacyMigrationService;
import fastcampus.ad.migration.application.legacyad.PageMigrationResult;
import fastcampus.ad.migration.application.legacyad.adgroup.PageLegacyAdGroupMigrationService;
import fastcampus.ad.migration.application.legacyad.keyword.PageLegacyKeywordMigrationService;
import fastcampus.ad.migration.domain.AggregateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PageMigrationDispatcher {

  private final PageLegacyAdGroupMigrationService adGroupMigrationService;
  private final PageLegacyKeywordMigrationService keywordMigrationService;

  public boolean dispatch(Long userId, AggregateType aggregateType) {
    PageLegacyMigrationService<?, ?, ?> service = switch (aggregateType) {
      case ADGROUP -> adGroupMigrationService;
      case KEYWORD -> keywordMigrationService;
      default -> throw new PageLegacyMigrationServiceNotFoundException();
    };
    PageMigrationResult result = service.migrate(userId);
    logMigrationResult(result, aggregateType);
    return result.isSuccess();
  }

  private void logMigrationResult(PageMigrationResult result, AggregateType aggregateType) {
    if (result.isSuccess()) {
      log.info("{}", PageLegacyMigrationLog.success(result, aggregateType));
    } else {
      log.error("{}", PageLegacyMigrationLog.fail(result, aggregateType));
    }
  }
}
