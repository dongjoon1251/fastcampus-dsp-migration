package fastcampus.ad.migration.gradual.domain.legacyad.adgroup;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyAdGroupRepository extends CrudRepository<LegacyAdGroup, Long> {

  List<LegacyAdGroup> findAllByCampaignIdAndDeletedAtIsNull(Long campaignId);

}
