package fastcampus.ad.migration.gradual.domain.legacyad.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyUserRepository extends CrudRepository<LegacyUser, Long> {

}
