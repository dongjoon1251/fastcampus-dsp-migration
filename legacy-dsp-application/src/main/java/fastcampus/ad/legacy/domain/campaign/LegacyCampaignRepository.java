package fastcampus.ad.legacy.domain.campaign;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyCampaignRepository extends CrudRepository<LegacyCampaign, Long> {

}
