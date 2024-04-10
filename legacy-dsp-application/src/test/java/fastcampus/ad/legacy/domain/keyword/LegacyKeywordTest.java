package fastcampus.ad.legacy.domain.keyword;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class LegacyKeywordTest {

  @Test
  void delete() {
    LegacyKeyword keyword = LegacyKeyword.of("키워드", 1L, 1L);

    LocalDateTime beforeDeleteDate = LocalDateTime.now();
    keyword.delete();
    LocalDateTime afterDeleteDate = LocalDateTime.now();

    assertThat(keyword.getDeletedAt())
        .isAfter(beforeDeleteDate)
        .isBefore(afterDeleteDate);
  }
}