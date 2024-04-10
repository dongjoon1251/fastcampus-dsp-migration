package fastcampus.ad.migration.gradual.application.legacyad.adgroup;

import fastcampus.ad.migration.gradual.application.legacyad.LegacyMigrationService;
import fastcampus.ad.migration.gradual.domain.legacyad.adgroup.LegacyAdGroup;
import fastcampus.ad.migration.gradual.domain.legacyad.adgroup.LegacyAdGroupRepository;
import fastcampus.ad.migration.gradual.domain.recentad.campaign.RecentCampaign;
import fastcampus.ad.migration.gradual.domain.recentad.campaign.RecentCampaignRepository;
import org.springframework.stereotype.Service;

@Service
public class LegacyAdGroupMigrationService extends
    LegacyMigrationService<LegacyAdGroup, RecentCampaign> {

  public LegacyAdGroupMigrationService(LegacyAdGroupRepository legacyAdGroupRepository,
      RecentCampaignRepository recentCampaignRepository,
      LegacyAdGroupConverter legacyAdGroupConverter) {
    super(legacyAdGroupRepository, recentCampaignRepository, legacyAdGroupConverter);
  }
}
