package fastcampus.ad.migration.gradual.application.legacyad.campaign;

import fastcampus.ad.migration.gradual.application.legacyad.LegacyMigrationService;
import fastcampus.ad.migration.gradual.application.legacyad.adgroup.LegacyAdGroupMigrationService;
import fastcampus.ad.migration.gradual.domain.legacyad.adgroup.LegacyAdGroup;
import fastcampus.ad.migration.gradual.domain.legacyad.adgroup.LegacyAdGroupRepository;
import fastcampus.ad.migration.gradual.domain.legacyad.campaign.LegacyCampaign;
import fastcampus.ad.migration.gradual.domain.recentad.campaign.RecentCampaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class LegacyCampaignMigrationService extends
    LegacyMigrationService<LegacyCampaign, RecentCampaign> {

  private final LegacyAdGroupMigrationService legacyAdGroupMigrationService;
  private final LegacyAdGroupRepository legacyAdGroupRepository;

  public LegacyCampaignMigrationService(
      CrudRepository<LegacyCampaign, Long> legacyRepository,
      CrudRepository<RecentCampaign, Long> recentRepository,
      LegacyAdGroupMigrationService legacyAdGroupMigrationService,
      LegacyAdGroupRepository legacyAdGroupRepository) {
    super(legacyRepository, recentRepository, null);
    this.legacyAdGroupMigrationService = legacyAdGroupMigrationService;
    this.legacyAdGroupRepository = legacyAdGroupRepository;
  }

  @Override
  public void migrate(LegacyCampaign legacyCampaign) {
    for (LegacyAdGroup legacyAdGroup : legacyAdGroupRepository.findAllByCampaignIdAndDeletedAtIsNull(
        legacyCampaign.getId())) {
      legacyAdGroupMigrationService.migrate(legacyAdGroup.getId());
    }
  }
}
