package fastcampus.ad.migration.gradual.application.legacyad.adgroup;

import fastcampus.ad.migration.gradual.application.legacyad.LegacyConverter;
import fastcampus.ad.migration.gradual.domain.legacyad.adgroup.LegacyAdGroup;
import fastcampus.ad.migration.gradual.domain.legacyad.campaign.LegacyCampaign;
import fastcampus.ad.migration.gradual.domain.legacyad.campaign.LegacyCampaignRepository;
import fastcampus.ad.migration.gradual.domain.recentad.campaign.RecentCampaign;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LegacyAdGroupConverter implements LegacyConverter<LegacyAdGroup, RecentCampaign> {

  private final LegacyCampaignRepository legacyCampaignRepository;

  public RecentCampaign convert(LegacyAdGroup legacyAdGroup) {
    LegacyCampaign legacyCampaign = legacyCampaignRepository.findById(legacyAdGroup.getCampaignId())
        .orElseThrow(EntityNotFoundException::new);
    return RecentCampaign.migrated(legacyAdGroup.getId(),
        legacyCampaign.getName() + "-" + legacyAdGroup.getName(),
        legacyCampaign.getUserId(), legacyCampaign.getBudget(), legacyAdGroup.getLinkUrl(),
        legacyAdGroup.getCreatedAt(), legacyAdGroup.getUpdatedAt(),
        legacyAdGroup.getDeletedAt());
  }
}
