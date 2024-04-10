package fastcampus.ad.migration.gradual.domain.recentad.campaign;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RecentCampaign {

  @Id
  private Long id;

  private String name;
  private Long userId;
  private Long budget;
  private String linkUrl;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;
  private LocalDateTime migratedAt;

}
