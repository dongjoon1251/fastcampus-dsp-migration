package fastcampus.ad.legacy.domain.campaign;


import fastcampus.ad.legacy.domain.campaign.event.LegacyCampaignBudgetUpdatedEvent;
import fastcampus.ad.legacy.domain.campaign.event.LegacyCampaignCreatedEvent;
import fastcampus.ad.legacy.domain.campaign.event.LegacyCampaignDeletedEvent;
import fastcampus.ad.legacy.domain.campaign.event.LegacyCampaignNameUpdatedEvent;
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
public class LegacyCampaign extends AbstractAggregateRoot<LegacyCampaign> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Long userId;
  private Long budget;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;

  private LegacyCampaign(String name, Long userId, Long budget, LocalDateTime createdAt) {
    this.name = name;
    this.userId = userId;
    this.budget = budget;
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
    this.deletedAt = null;
    registerEvent(new LegacyCampaignCreatedEvent(this));
  }

  public static LegacyCampaign of(String name, Long userId, Long budget) {
    return new LegacyCampaign(name, userId, budget, LocalDateTime.now());
  }

  public void updateName(String name) {
    this.name = name;
    updatedAt = LocalDateTime.now();
    registerEvent(new LegacyCampaignNameUpdatedEvent(this));
  }

  public void updateBudget(Long budget) {
    this.budget = budget;
    updatedAt = LocalDateTime.now();
    registerEvent(new LegacyCampaignBudgetUpdatedEvent(this));
  }

  public void delete() {
    deletedAt = LocalDateTime.now();
    registerEvent(new LegacyCampaignDeletedEvent(this));
  }
}
