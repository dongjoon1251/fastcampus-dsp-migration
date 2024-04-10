package fastcampus.ad.legacy.domain.keyword;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class LegacyKeyword {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;
  private Long adGroupId;
  private Long userId;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;

  private LegacyKeyword(String text, Long adGroupId, Long userId, LocalDateTime createdAt) {
    this.text = text;
    this.adGroupId = adGroupId;
    this.userId = userId;
    this.createdAt = createdAt;
    this.deletedAt = null;
  }

  public static LegacyKeyword of(String text, Long adGroupId, Long userId) {
    return new LegacyKeyword(text, adGroupId, userId, LocalDateTime.now());
  }

  public void delete() {
    deletedAt = LocalDateTime.now();
  }
}
