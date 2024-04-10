package fastcampus.ad.migration.gradual.domain.recentad.campaign;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentCampaignRepository extends CrudRepository<RecentCampaign, Long> {

}
