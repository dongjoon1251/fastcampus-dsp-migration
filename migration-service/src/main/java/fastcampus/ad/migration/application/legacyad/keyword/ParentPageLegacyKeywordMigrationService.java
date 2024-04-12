package fastcampus.ad.migration.application.legacyad.keyword;

import fastcampus.ad.migration.application.legacyad.ParentPageLegacyMigrationService;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroup;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroupRepository;
import fastcampus.ad.migration.domain.legacyad.keyword.LegacyKeyword;
import fastcampus.ad.migration.domain.legacyad.keyword.LegacyKeywordRepository;
import fastcampus.ad.migration.domain.migration.keyword.KeywordPageMigration;
import fastcampus.ad.migration.domain.migration.keyword.KeywordPageMigrationRepository;
import fastcampus.ad.migration.domain.recentad.keyword.RecentKeyword;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ParentPageLegacyKeywordMigrationService extends
    ParentPageLegacyMigrationService<KeywordPageMigration, LegacyKeyword, RecentKeyword, LegacyAdGroup> {

  public ParentPageLegacyKeywordMigrationService(
      KeywordPageMigrationRepository keywordMigrationRepository,
      LegacyKeywordRepository legacyKeywordRepository,
      LegacyKeywordMigrationService legacyKeywordMigrationService,
      LegacyAdGroupRepository legacyAdGroupRepository) {
    super(keywordMigrationRepository, legacyKeywordRepository, legacyKeywordMigrationService,
        legacyAdGroupRepository);
  }

  @Override
  protected KeywordPageMigration firstPageMigration(Long userId, boolean isSuccess,
      Page<LegacyKeyword> legacyPage) {
    return KeywordPageMigration.first(isSuccess, userId, PARENT_PAGE_SIZE,
        legacyPage.getTotalElements());
  }

  @Override
  protected List<LegacyKeyword> findAllByParentIdsIn(List<Long> legacyParentIds) {
    return ((LegacyKeywordRepository) legacyPageableRepository).findAllByAdGroupIdInAndDeletedAtIsNull(
        legacyParentIds);
  }
}
