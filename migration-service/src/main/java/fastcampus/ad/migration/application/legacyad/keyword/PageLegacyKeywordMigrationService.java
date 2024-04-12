package fastcampus.ad.migration.application.legacyad.keyword;

import fastcampus.ad.migration.application.legacyad.PageLegacyMigrationService;
import fastcampus.ad.migration.domain.legacyad.keyword.LegacyKeyword;
import fastcampus.ad.migration.domain.legacyad.keyword.LegacyKeywordRepository;
import fastcampus.ad.migration.domain.migration.keyword.KeywordPageMigration;
import fastcampus.ad.migration.domain.migration.keyword.KeywordPageMigrationRepository;
import fastcampus.ad.migration.domain.recentad.keyword.RecentKeyword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PageLegacyKeywordMigrationService extends
    PageLegacyMigrationService<KeywordPageMigration, LegacyKeyword, RecentKeyword> {

  public PageLegacyKeywordMigrationService(
      KeywordPageMigrationRepository keywordMigrationRepository,
      LegacyKeywordRepository legacyKeywordRepository,
      LegacyKeywordMigrationService legacyKeywordMigrationService) {
    super(keywordMigrationRepository, legacyKeywordRepository, legacyKeywordMigrationService);
  }

  @Override
  protected KeywordPageMigration firstPageMigration(Long userId, boolean isSuccess,
      Page<LegacyKeyword> legacyPage) {
    return KeywordPageMigration.first(isSuccess, userId, PAGE_SIZE, legacyPage.getTotalElements());
  }
}
