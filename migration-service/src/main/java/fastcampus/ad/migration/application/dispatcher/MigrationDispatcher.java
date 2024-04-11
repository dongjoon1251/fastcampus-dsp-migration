package fastcampus.ad.migration.application.dispatcher;

import fastcampus.ad.migration.application.legacyad.MigrationService;
import fastcampus.ad.migration.application.legacyad.adgroup.LegacyAdGroupMigrationService;
import fastcampus.ad.migration.application.legacyad.campaign.LegacyCampaignMigrationService;
import fastcampus.ad.migration.application.legacyad.keyword.LegacyKeywordMigrationService;
import fastcampus.ad.migration.application.legacyad.user.LegacyUserMigrationService;
import fastcampus.ad.migration.application.user.MigrationUserService;
import fastcampus.ad.migration.domain.AggregateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationDispatcher {

  private final MigrationUserService migrationUserService;
  private final LegacyUserMigrationService legacyUserMigrationService;
  private final LegacyCampaignMigrationService legacyCampaignMigrationService;
  private final LegacyAdGroupMigrationService legacyAdGroupMigrationService;
  private final LegacyKeywordMigrationService legacyKeywordMigrationService;


  public boolean dispatch(Long userId, Long aggregateId, AggregateType aggregateType) {
    if (isDisagreed(userId)) {
      return false;
    }
    return migrate(aggregateId, aggregateType);
  }
  
  private boolean isDisagreed(Long userId) {
    return migrationUserService.isDisagreed(userId);
  }


  private boolean migrate(Long aggregateId, AggregateType aggregateType) {
    MigrationService service = switch (aggregateType) {
      case USER -> legacyUserMigrationService;
      case CAMPAIGN -> legacyCampaignMigrationService;
      case ADGROUP -> legacyAdGroupMigrationService;
      case KEYWORD -> legacyKeywordMigrationService;
    };
    boolean result = service.migrate(aggregateId);
    logMigrationResult(result, aggregateType, aggregateId);
    return result;
  }

  private void logMigrationResult(boolean result, AggregateType aggregateType, Long aggregateId) {
    if (result) {
      log.info("{}", LegacyMigrationLog.success(aggregateType, aggregateId));
    } else {
      log.error("{}", LegacyMigrationLog.fail(aggregateType, aggregateId));
    }
  }
}
