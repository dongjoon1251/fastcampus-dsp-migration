package fastcampus.ad.migration.domain.migration;

import java.util.stream.StreamSupport;

public record PageMigrations(Iterable<? extends PageMigration<?>> pageMigrations) {


  public static PageMigrations of(Iterable<? extends PageMigration<?>> pageMigrations) {
    return new PageMigrations(pageMigrations);
  }


  public Long migratedCount() {
    return StreamSupport.stream(pageMigrations.spliterator(), true)
        .filter(PageMigration::isNotEmpty)
        .map(pageMigration -> {
          if (pageMigration.isFinished()) {
            return pageMigration.getTotalCount();
          }
          return (long) pageMigration.nextPageNumber() * pageMigration.pageSize;
        })
        .mapToLong(Long::longValue)
        .sum();
  }

  public Long totalCount() {
    return StreamSupport.stream(pageMigrations.spliterator(), true)
        .filter(PageMigration::isNotEmpty)
        .map(PageMigration::getTotalCount)
        .mapToLong(Long::longValue)
        .sum();
  }

}
