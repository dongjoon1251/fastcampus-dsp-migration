package fastcampus.ad.migration.domain.migration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class PageMigrationsTest {

  @Test
  void migratedCount() {
    PageMigrations pageMigrations = PageMigrations.of(List.of(new PageMigrationTestable(1L, 4,
        10, 100L), new PageMigrationTestable(2L, 3,
        10, 100L), new PageMigrationTestable(3L, 0,
        10, 0L)));

    Long result = pageMigrations.migratedCount();

    assertThat(result).isEqualTo(90);
  }

  @Test
  void totalCount() {
    PageMigrations pageMigrations = PageMigrations.of(List.of(new PageMigrationTestable(1L, 4,
        10, 100L), new PageMigrationTestable(2L, 3,
        10, 100L), new PageMigrationTestable(3L, 0,
        10, 0L)));

    Long result = pageMigrations.totalCount();

    assertThat(result).isEqualTo(200);
  }
}