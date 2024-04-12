package fastcampus.ad.migration.domain.migration.adgroup;

import fastcampus.ad.migration.domain.migration.PageMigrationRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdGroupPageMigrationRepository extends
    PageMigrationRepository<AdGroupPageMigration> {

}
