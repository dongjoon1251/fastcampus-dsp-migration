package fastcampus.ad.legacy.domain.adgroup;


import fastcampus.ad.legacy.domain.adgroup.event.LegacyAdGroupCreatedEvent;
import fastcampus.ad.legacy.domain.adgroup.event.LegacyAdGroupDeletedEvent;
import fastcampus.ad.legacy.domain.adgroup.event.LegacyAdGroupLinkUrlUpdatedEvent;
import fastcampus.ad.legacy.domain.adgroup.event.LegacyAdGroupNameUpdatedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@NoArgsConstructor
@Getter
public class LegacyAdGroup extends AbstractAggregateRoot<LegacyAdGroup> {

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
    registerEvent(new LegacyAdGroupCreatedEvent(this));
  }

  public static LegacyAdGroup of(String name, Long campaignId, Long userId, String linkUrl) {
    return new LegacyAdGroup(name, campaignId, userId, linkUrl, LocalDateTime.now());
  }

  public void updateName(String name) {
    this.name = name;
    updatedAt = LocalDateTime.now();
    registerEvent(new LegacyAdGroupNameUpdatedEvent(this));
  }

  public void updateLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
    updatedAt = LocalDateTime.now();
    registerEvent(new LegacyAdGroupLinkUrlUpdatedEvent(this));
  }

  public void delete() {
    deletedAt = LocalDateTime.now();
    registerEvent(new LegacyAdGroupDeletedEvent(this));
  }
}
