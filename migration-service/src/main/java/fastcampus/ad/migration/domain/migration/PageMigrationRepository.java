package fastcampus.ad.migration.domain.migration;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PageMigrationRepository<T extends PageMigration<T>> extends
    CrudRepository<T, Long> {

}
