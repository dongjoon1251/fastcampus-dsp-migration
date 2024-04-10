package fastcampus.ad.migration.gradual.domain.recentad;

import java.time.LocalDateTime;

public interface MigratedEntity {

  LocalDateTime getMigratedAt();

}
