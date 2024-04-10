package fastcampus.ad.legacy.domain.campaign;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class LegacyCampaignTest {

  LegacyCampaign campaign = LegacyCampaign.of("캠페인명", 1L, 100L);

  @Test
  void updateName() {
    campaign.updateName("newCampaignName");

    assertThat(campaign.getName()).isEqualTo("newCampaignName");
  }

  @Test
  void updateBudget() {
    campaign.updateBudget(2000L);

    assertThat(campaign.getBudget()).isEqualTo(2000L);
  }

  @Test
  void delete() {
    LocalDateTime beforeDeleteDate = LocalDateTime.now();
    campaign.delete();
    LocalDateTime afterDeleteDate = LocalDateTime.now();

    assertThat(campaign.getDeletedAt())
        .isAfter(beforeDeleteDate)
        .isBefore(afterDeleteDate);
  }
}