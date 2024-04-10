package fastcampus.ad.legacy.domain.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class LegacyUserTest {

  LegacyUser user = LegacyUser.of("name");

  @Test
  void updateName() {
    user.updateName("newName");

    assertThat(user.getName()).isEqualTo("newName");
  }

  @Test
  void delete() {
    LocalDateTime beforeDeleteDate = LocalDateTime.now();
    user.delete();
    LocalDateTime afterDeleteDate = LocalDateTime.now();

    assertThat(user.getDeletedAt())
        .isAfter(beforeDeleteDate)
        .isBefore(afterDeleteDate);
  }
}