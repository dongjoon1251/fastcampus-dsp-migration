package fastcampus.ad.legacy.domain.adgroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class LegacyAdGroupTest {

  LegacyAdGroup adGroup = LegacyAdGroup.of("광고그룹명", 1L, 1L, "http://fastcampus.com");

  @Test
  void updateName() {
    adGroup.updateName("newAdGroupName");

    assertThat(adGroup.getName()).isEqualTo("newAdGroupName");
  }

  @Test
  void updateLinkUrl() {
    adGroup.updateLinkUrl("http://newfastcampus.com");

    assertThat(adGroup.getLinkUrl()).isEqualTo("http://newfastcampus.com");
  }

  @Test
  void delete() {
    LocalDateTime beforeDeleteDate = LocalDateTime.now();
    adGroup.delete();
    LocalDateTime afterDeleteDate = LocalDateTime.now();

    assertThat(adGroup.getDeletedAt())
        .isAfter(beforeDeleteDate)
        .isBefore(afterDeleteDate);
  }
}