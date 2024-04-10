package fastcampus.ad.migration.gradual.domain.legacyad.keyword;


import fastcampus.ad.migration.gradual.domain.legacyad.DeletableEntity;
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
public class LegacyKeyword implements DeletableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;
  private Long adGroupId;
  private Long userId;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;

}

