package fastcampus.ad.migration.application.monitoring;

import fastcampus.ad.migration.domain.migration.PageMigrations;
import fastcampus.ad.migration.domain.migration.adgroup.AdGroupPageMigrationRepository;
import fastcampus.ad.migration.domain.migration.keyword.KeywordPageMigrationRepository;
import fastcampus.ad.migration.domain.migration.user.MigrationUserRepository;
import fastcampus.ad.migration.domain.migration.user.MigrationUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MigrationMonitor {

  private final MigrationUserRepository migrationUserRepository;
  private final AdGroupPageMigrationRepository adGroupPageMigrationRepository;
  private final KeywordPageMigrationRepository keywordPageMigrationRepository;

  public MigrationMonitorMetrics measure() {
    MigrationUsers migrationUsers = MigrationUsers.of(migrationUserRepository.findAll());
    PageMigrations adGroupMigrations = PageMigrations.of(adGroupPageMigrationRepository.findAll());
    PageMigrations keywordMigrations = PageMigrations.of(keywordPageMigrationRepository.findAll());

    return MigrationMonitorMetrics.from(migrationUsers, adGroupMigrations, keywordMigrations);
  }

}
