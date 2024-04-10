package fastcampus.ad.migration.gradual.domain.recentad.keyword;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RecentKeyword {

  @Id
  private Long id;

  private String text;
  private Long campaignId;
  private Long userId;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;
  private LocalDateTime migratedAt;

}

