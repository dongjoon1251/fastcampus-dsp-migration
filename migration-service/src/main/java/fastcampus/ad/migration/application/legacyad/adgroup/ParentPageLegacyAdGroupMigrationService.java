package fastcampus.ad.migration.application.legacyad.adgroup;

import fastcampus.ad.migration.application.legacyad.ParentPageLegacyMigrationService;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroup;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroupRepository;
import fastcampus.ad.migration.domain.legacyad.campaign.LegacyCampaign;
import fastcampus.ad.migration.domain.legacyad.campaign.LegacyCampaignRepository;
import fastcampus.ad.migration.domain.migration.adgroup.AdGroupPageMigration;
import fastcampus.ad.migration.domain.migration.adgroup.AdGroupPageMigrationRepository;
import fastcampus.ad.migration.domain.recentad.campaign.RecentCampaign;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ParentPageLegacyAdGroupMigrationService extends
    ParentPageLegacyMigrationService<AdGroupPageMigration, LegacyAdGroup, RecentCampaign, LegacyCampaign> {

  public ParentPageLegacyAdGroupMigrationService(
      AdGroupPageMigrationRepository adGroupMigrationRepository,
      LegacyAdGroupRepository legacyAdGroupRepository,
      LegacyAdGroupMigrationService legacyAdGroupMigrationService,
      LegacyCampaignRepository legacyCampaignRepository) {
    super(adGroupMigrationRepository, legacyAdGroupRepository, legacyAdGroupMigrationService,
        legacyCampaignRepository);
  }

  @Override
  protected AdGroupPageMigration firstPageMigration(Long userId, boolean isSuccess,
      Page<LegacyAdGroup> legacyPage) {
    return AdGroupPageMigration.first(isSuccess, userId, PARENT_PAGE_SIZE,
        legacyPage.getTotalElements());
  }

  @Override
  protected List<LegacyAdGroup> findAllByParentIdsIn(List<Long> legacyParentIds) {
    return ((LegacyAdGroupRepository) legacyPageableRepository).findAllByCampaignIdInAndDeletedAtIsNull(
        legacyParentIds);
  }
}
