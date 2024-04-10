package fastcampus.ad.migration.gradual.application.legacyad.user;

import fastcampus.ad.migration.gradual.application.legacyad.LegacyMigrationService;
import fastcampus.ad.migration.gradual.domain.legacyad.user.LegacyUser;
import fastcampus.ad.migration.gradual.domain.legacyad.user.LegacyUserRepository;
import fastcampus.ad.migration.gradual.domain.recentad.user.RecentUser;
import fastcampus.ad.migration.gradual.domain.recentad.user.RecentUserRepository;
import org.springframework.stereotype.Service;

@Service
public class LegacyUserMigrationService extends LegacyMigrationService<LegacyUser, RecentUser> {

  public LegacyUserMigrationService(LegacyUserRepository legacyUserRepository,
      RecentUserRepository recentUserRepository, LegacyUserConverter legacyUserConverter) {
    super(legacyUserRepository, recentUserRepository, legacyUserConverter);
  }
}
