package fastcampus.ad.migration.domain.legacyad.keyword;

import fastcampus.ad.migration.domain.legacyad.LegacyPageableRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyKeywordRepository extends LegacyPageableRepository<LegacyKeyword> {

  List<LegacyKeyword> findAllByAdGroupIdInAndDeletedAtIsNull(List<Long> adGroupIds);

}
