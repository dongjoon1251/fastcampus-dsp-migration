package fastcampus.ad.migration.gradual.application.legacyad.keyword;

import fastcampus.ad.migration.gradual.application.legacyad.LegacyMigrationService;
import fastcampus.ad.migration.gradual.domain.legacyad.keyword.LegacyKeyword;
import fastcampus.ad.migration.gradual.domain.legacyad.keyword.LegacyKeywordRepository;
import fastcampus.ad.migration.gradual.domain.recentad.keyword.RecentKeyword;
import fastcampus.ad.migration.gradual.domain.recentad.keyword.RecentKeywordRepository;
import org.springframework.stereotype.Service;

@Service
public class LegacyKeywordMigrationService extends
    LegacyMigrationService<LegacyKeyword, RecentKeyword> {

  public LegacyKeywordMigrationService(LegacyKeywordRepository legacyKeywordRepository,
      RecentKeywordRepository recentKeywordRepository,
      LegacyKeywordConverter legacyKeywordConverter) {
    super(legacyKeywordRepository, recentKeywordRepository, legacyKeywordConverter);
  }
}
