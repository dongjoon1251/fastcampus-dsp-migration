package fastcampus.ad.migration.domain.migration.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigrationUserRepository extends CrudRepository<MigrationUser, Long> {

}