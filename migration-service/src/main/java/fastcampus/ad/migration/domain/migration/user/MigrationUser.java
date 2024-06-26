package fastcampus.ad.migration.domain.migration.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@NoArgsConstructor
@Getter
public class MigrationUser extends AbstractAggregateRoot<MigrationUser> {

  @Id
  private Long id;

  @Enumerated(EnumType.STRING)
  private MigrationUserStatus status;
  private LocalDateTime agreedAt;
  private LocalDateTime updateAt;
  @Enumerated(EnumType.STRING)
  private MigrationUserStatus prevStatus;

  private MigrationUser(Long id, LocalDateTime agreedAt) {
    this.id = id;
    this.status = MigrationUserStatus.AGREED;
    this.agreedAt = agreedAt;
    this.updateAt = agreedAt;
    registerEvent(new MigrationAgreedEvent(this));
  }

  public static MigrationUser agreed(Long id) {
    return new MigrationUser(id, LocalDateTime.now());
  }

  public void progressMigration() {
    if (MigrationUserStatus.RETRIED.equals(status)) {
      status = prevStatus.next();
    } else {
      prevStatus = status;
      status = status.next();
    }
    updateAt = LocalDateTime.now();
    registerEvent(new MigrationProgressedEvent(this));
  }

  public void retry() {
    if (!MigrationUserStatus.RETRIED.equals(status)) {
      prevStatus = status;
      status = MigrationUserStatus.RETRIED;
    }
    updateAt = LocalDateTime.now();
    registerEvent(new MigrationRetriedEvent(this));
  }
}
