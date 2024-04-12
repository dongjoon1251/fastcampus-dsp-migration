package fastcampus.ad.migration.application.legacyad;

import fastcampus.ad.migration.domain.legacyad.DeletableEntity;
import fastcampus.ad.migration.domain.legacyad.LegacyPageableRepository;
import fastcampus.ad.migration.domain.migration.PageMigration;
import fastcampus.ad.migration.domain.migration.PageMigrationRepository;
import fastcampus.ad.migration.domain.recentad.MigratedEntity;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@Slf4j
public abstract class ParentPageLegacyMigrationService<P extends PageMigration<P>, Legacy extends DeletableEntity, Recent extends MigratedEntity, LegacyParent extends DeletableEntity> extends
    PageLegacyMigrationService<P, Legacy, Recent> {

  public final static Integer PARENT_PAGE_SIZE = 10;
  protected final LegacyPageableRepository<LegacyParent> parentRepository;

  public ParentPageLegacyMigrationService(
      PageMigrationRepository<P> pageMigrationRepository,
      LegacyPageableRepository<Legacy> legacyPageableRepository,
      LegacyMigrationService<Legacy, Recent> legacyMigrationService,
      LegacyPageableRepository<LegacyParent> parentRepository) {
    super(pageMigrationRepository, legacyPageableRepository, legacyMigrationService);
    this.parentRepository = parentRepository;
  }


  @Override
  protected Page<Legacy> findPage(Long userId, Integer pageNumber) {
    Page<LegacyParent> legacyParentPage = findLegacyParentPage(userId, pageNumber);
    List<Long> legacyParentIds = getLegacyParentIds(legacyParentPage);
    List<Legacy> legacies = findAllByParentIdsIn(legacyParentIds);
    return new PageImpl<>(legacies, PageRequest.of(pageNumber, PARENT_PAGE_SIZE),
        legacyParentPage.getTotalElements());
  }

  private Page<LegacyParent> findLegacyParentPage(Long userId, Integer pageNumber) {
    return parentRepository.findAllByUserIdAndDeletedAtIsNullOrderById(
        userId, PageRequest.of(pageNumber, PARENT_PAGE_SIZE));
  }

  private List<Long> getLegacyParentIds(Page<LegacyParent> legacyParentPage) {
    return legacyParentPage.stream()
        .map(DeletableEntity::getId)
        .toList();
  }

  protected abstract List<Legacy> findAllByParentIdsIn(List<Long> legacyParentIds);

}
