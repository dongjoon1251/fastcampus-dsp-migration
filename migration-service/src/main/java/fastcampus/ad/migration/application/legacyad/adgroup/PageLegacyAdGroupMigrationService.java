package fastcampus.ad.migration.application.legacyad.adgroup;

import fastcampus.ad.migration.application.legacyad.PageLegacyMigrationService;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroup;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroupRepository;
import fastcampus.ad.migration.domain.migration.adgroup.AdGroupPageMigration;
import fastcampus.ad.migration.domain.migration.adgroup.AdGroupPageMigrationRepository;
import fastcampus.ad.migration.domain.recentad.campaign.RecentCampaign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PageLegacyAdGroupMigrationService extends
    PageLegacyMigrationService<AdGroupPageMigration, LegacyAdGroup, RecentCampaign> {

  public PageLegacyAdGroupMigrationService(
      AdGroupPageMigrationRepository adGroupMigrationRepository,
      LegacyAdGroupRepository legacyAdGroupRepository,
      LegacyAdGroupMigrationService legacyAdGroupMigrationService) {
    super(adGroupMigrationRepository, legacyAdGroupRepository, legacyAdGroupMigrationService);
  }

  @Override
  protected AdGroupPageMigration firstPageMigration(Long userId, boolean isSuccess,
      Page<LegacyAdGroup> legacyPage) {
    return AdGroupPageMigration.first(isSuccess, userId, PAGE_SIZE, legacyPage.getTotalElements());
  }
}
