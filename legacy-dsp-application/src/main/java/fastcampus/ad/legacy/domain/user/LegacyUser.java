package fastcampus.ad.legacy.domain.user;


import fastcampus.ad.legacy.domain.user.event.LegacyUserCreatedEvent;
import fastcampus.ad.legacy.domain.user.event.LegacyUserDeletedEvent;
import fastcampus.ad.legacy.domain.user.event.LegacyUserNameUpdatedEvent;
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
public class LegacyUser extends AbstractAggregateRoot<LegacyUser> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;

  private LegacyUser(String name, LocalDateTime createdAt) {
    this.name = name;
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
    this.deletedAt = null;
    registerEvent(new LegacyUserCreatedEvent(this));
  }

  public static LegacyUser of(String name) {
    return new LegacyUser(name, LocalDateTime.now());
  }

  public void updateName(String name) {
    this.name = name;
    updatedAt = LocalDateTime.now();
    registerEvent(new LegacyUserNameUpdatedEvent(this));
  }

  public void delete() {
    deletedAt = LocalDateTime.now();
    registerEvent(new LegacyUserDeletedEvent(this));
  }
}
