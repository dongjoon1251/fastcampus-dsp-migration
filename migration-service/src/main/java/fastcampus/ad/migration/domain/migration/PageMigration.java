package fastcampus.ad.migration.domain.migration;

import fastcampus.ad.migration.domain.AggregateType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class PageMigration<T extends AbstractAggregateRoot<T>> extends
    AbstractAggregateRoot<T> {

  public final static Integer NOT_STARTED_PAGE_NUMBER = -1;
  public final static Integer INIT_PAGE_NUMBER = 0;
  public final static Integer PAGE_INCREMENT = 1;

  @Id
  protected Long id;
  protected Integer pageSize;
  protected Integer pageNumber;
  protected Long totalCount;
  protected LocalDateTime createdAt;
  protected LocalDateTime updatedAt;

  protected PageMigration(Long id, Integer pageSize, Integer pageNumber, Long totalCount,
      LocalDateTime createdAt) {
    this.id = id;
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
    this.totalCount = totalCount;
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
    registerEvent(new PageMigrationEvent(aggregateType(), id, isFinished()));
  }

  protected PageMigration(Long id, Integer pageNumber, Integer pageSize, Long totalCount) {
    this(id, pageSize, pageNumber, totalCount, LocalDateTime.now());
  }

  protected abstract AggregateType aggregateType();


  public Integer nextPageNumber() {
    return pageNumber + PAGE_INCREMENT;
  }

  public boolean isFinished() {
    return (long) pageSize * nextPageNumber() >= totalCount && pageNumber > NOT_STARTED_PAGE_NUMBER;
  }

  public void progress(boolean migrationSuccess, Long totalCount) {
    if (migrationSuccess && !isFinished()) {
      pageNumber = nextPageNumber();
    }
    this.totalCount = totalCount;
    updatedAt = LocalDateTime.now();
    registerEvent(new PageMigrationEvent(aggregateType(), id, isFinished()));
  }

  public boolean isNotEmpty() {
    return totalCount != 0;
  }
}
