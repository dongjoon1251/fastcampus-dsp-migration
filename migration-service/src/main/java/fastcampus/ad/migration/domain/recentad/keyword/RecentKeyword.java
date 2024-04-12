package fastcampus.ad.migration.domain.recentad.keyword;


import fastcampus.ad.migration.domain.recentad.MigratedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RecentKeyword implements MigratedEntity {

  @Id
  private Long id;

  private String text;
  private Long campaignId;
  private Long userId;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;
  private LocalDateTime migratedAt;

  public RecentKeyword(Long id, String text, Long campaignId, Long userId, LocalDateTime createdAt,
      LocalDateTime deletedAt, LocalDateTime migratedAt) {
    this.id = id;
    this.text = text;
    this.campaignId = campaignId;
    this.userId = userId;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
    this.migratedAt = migratedAt;
  }

  public static RecentKeyword migrated(Long id, String text, Long campaignId, Long userId,
      LocalDateTime createdAt, LocalDateTime deletedAt) {
    return new RecentKeyword(id, text, campaignId, userId, createdAt, deletedAt,
        LocalDateTime.now());
  }
}

