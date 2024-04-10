package fastcampus.ad.migration.gradual.domain.recentad.user;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RecentUser {

  @Id
  private Long id;

  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;
  private LocalDateTime migratedAt;

}
