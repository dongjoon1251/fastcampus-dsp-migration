package fastcampus.ad.migration.gradual.domain.legacyad.campaign;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyCampaignRepository extends CrudRepository<LegacyCampaign, Long> {

}
