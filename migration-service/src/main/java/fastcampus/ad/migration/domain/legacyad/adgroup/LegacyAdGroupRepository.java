package fastcampus.ad.migration.domain.legacyad.adgroup;

import fastcampus.ad.migration.domain.legacyad.LegacyPageableRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyAdGroupRepository extends LegacyPageableRepository<LegacyAdGroup> {

  List<LegacyAdGroup> findAllByCampaignIdAndDeletedAtIsNull(Long campaignId);

  List<LegacyAdGroup> findAllByCampaignIdInAndDeletedAtIsNull(List<Long> campaignIds);
}
