package fastcampus.ad.legacy.domain.adgroup;


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
public class LegacyAdGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Long campaignId;
  private Long userId;
  private String linkUrl;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;

  private LegacyAdGroup(String name, Long campaignId, Long userId, String linkUrl,
      LocalDateTime createdAt) {
    this.name = name;
    this.campaignId = campaignId;
    this.userId = userId;
    this.linkUrl = linkUrl;
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
    this.deletedAt = null;
  }

  public static LegacyAdGroup of(String name, Long campaignId, Long userId, String linkUrl) {
    return new LegacyAdGroup(name, campaignId, userId, linkUrl, LocalDateTime.now());
  }

  public void updateName(String name) {
    this.name = name;
    updatedAt = LocalDateTime.now();
  }

  public void updateLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
    updatedAt = LocalDateTime.now();
  }

  public void delete() {
    deletedAt = LocalDateTime.now();
  }
}
