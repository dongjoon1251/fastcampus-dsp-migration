package fastcampus.ad.legacy.domain.keyword;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyKeywordRepository extends CrudRepository<LegacyKeyword, Long> {

}
