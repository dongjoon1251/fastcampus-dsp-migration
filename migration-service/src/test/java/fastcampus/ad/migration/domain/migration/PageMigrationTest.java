package fastcampus.ad.migration.domain.migration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PageMigrationTest {


  @ValueSource(ints = {0, 1, 7, 8})
  @ParameterizedTest
  void nextPageNumber(int pageNumber) {
    PageMigration<PageMigrationTestable> pageMigration = new PageMigrationTestable(1L, pageNumber,
        10, 100L);

    Integer nextPageNumber = pageMigration.nextPageNumber();

    assertThat(nextPageNumber).isEqualTo(pageNumber + PageMigration.PAGE_INCREMENT);
  }

  @ValueSource(ints = {0, 1, 7, 8})
  @ParameterizedTest
  void isNotFinished(int pageNumber) {
    PageMigration<PageMigrationTestable> pageMigration = new PageMigrationTestable(1L, pageNumber,
        10, 100L);

    boolean isFinished = pageMigration.isFinished();

    assertThat(isFinished).isFalse();
  }

  @ValueSource(ints = {9, 10, 11})
  @ParameterizedTest
  void isFinished(int pageNumber) {
    PageMigration<PageMigrationTestable> pageMigration = new PageMigrationTestable(1L, pageNumber,
        10, 100L);

    boolean isFinished = pageMigration.isFinished();

    assertThat(isFinished).isTrue();
  }

  @Test
  void progressSuccess() {
    PageMigration<PageMigrationTestable> pageMigration = new PageMigrationTestable(1L, 5,
        10, 100L);

    LocalDateTime beforeUpdate = LocalDateTime.now();
    pageMigration.progress(true, 110L);
    LocalDateTime afterUpdate = LocalDateTime.now();

    assertAll(
        () -> assertThat(pageMigration.pageNumber).isEqualTo(5 + PageMigration.PAGE_INCREMENT),
        () -> assertThat(pageMigration.totalCount).isEqualTo(110L),
        () -> assertThat(pageMigration.updatedAt).isAfter(beforeUpdate).isBefore(afterUpdate)
    );
  }

  @Test
  void progressFail() {
    PageMigration<PageMigrationTestable> pageMigration = new PageMigrationTestable(1L, 5,
        10, 100L);

    LocalDateTime beforeUpdate = LocalDateTime.now();
    pageMigration.progress(false, 110L);
    LocalDateTime afterUpdate = LocalDateTime.now();

    assertAll(
        () -> assertThat(pageMigration.pageNumber).isEqualTo(5),
        () -> assertThat(pageMigration.totalCount).isEqualTo(110L),
        () -> assertThat(pageMigration.updatedAt).isAfter(beforeUpdate).isBefore(afterUpdate)
    );
  }

  @Test
  void progressSuccess여도_이미_finished된거라면_page_number_증가_안시킴() {
    PageMigration<PageMigrationTestable> pageMigration = new PageMigrationTestable(1L, 9,
        10, 100L);

    LocalDateTime beforeUpdate = LocalDateTime.now();
    pageMigration.progress(true, 100L);
    LocalDateTime afterUpdate = LocalDateTime.now();

    assertAll(
        () -> assertThat(pageMigration.pageNumber).isEqualTo(9),
        () -> assertThat(pageMigration.totalCount).isEqualTo(100L),
        () -> assertThat(pageMigration.updatedAt).isAfter(beforeUpdate).isBefore(afterUpdate)
    );
  }

}