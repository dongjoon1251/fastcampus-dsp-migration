package fastcampus.ad.migration.domain.migration;

import fastcampus.ad.migration.domain.AggregateType;

public class PageMigrationTestable extends PageMigration<PageMigrationTestable> {

  public PageMigrationTestable(Long id, Integer pageNumber, Integer pageSize, Long totalCount) {
    super(id, pageNumber, pageSize, totalCount);
  }

  @Override
  protected AggregateType aggregateType() {
    return AggregateType.ADGROUP;
  }
}
