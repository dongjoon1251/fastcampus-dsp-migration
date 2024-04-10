package fastcampus.ad.legacy.application.keyword;

import fastcampus.ad.legacy.domain.keyword.LegacyKeyword;
import java.time.LocalDateTime;

public record LegacyKeywordResult(Long id, String text, Long adGroupId, Long userId,
                                  LocalDateTime createdAt,
                                  LocalDateTime deletedAt) {

  public static LegacyKeywordResult from(LegacyKeyword keyword) {
    return new LegacyKeywordResult(keyword.getId(), keyword.getText(), keyword.getAdGroupId(),
        keyword.getUserId(), keyword.getCreatedAt(), keyword.getDeletedAt());
  }
}
