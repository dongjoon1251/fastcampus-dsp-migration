package fastcampus.ad.legacy.domain.keyword;


import fastcampus.ad.legacy.domain.keyword.event.LegacyKeywordCreatedEvent;
import fastcampus.ad.legacy.domain.keyword.event.LegacyKeywordDeletedEvent;
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
public class LegacyKeyword extends AbstractAggregateRoot<LegacyKeyword> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;
  private Long adGroupId;
  private Long userId;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;

  private LegacyKeyword(String text, Long adGroupId, Long userId, LocalDateTime createdAt,
      LocalDateTime deletedAt) {
    this.text = text;
    this.adGroupId = adGroupId;
    this.userId = userId;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
    registerEvent(new LegacyKeywordCreatedEvent(this));
  }

  public static LegacyKeyword of(String text, Long adGroupId, Long userId) {
    return new LegacyKeyword(text, adGroupId, userId, LocalDateTime.now(), null);
  }

  public void delete() {
    deletedAt = LocalDateTime.now();
    registerEvent(new LegacyKeywordDeletedEvent(this));
  }
}
