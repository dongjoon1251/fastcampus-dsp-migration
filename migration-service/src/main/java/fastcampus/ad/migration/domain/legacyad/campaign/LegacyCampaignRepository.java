package fastcampus.ad.migration.domain.legacyad.campaign;

import fastcampus.ad.migration.domain.legacyad.LegacyPageableRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyCampaignRepository extends LegacyPageableRepository<LegacyCampaign> {

}
