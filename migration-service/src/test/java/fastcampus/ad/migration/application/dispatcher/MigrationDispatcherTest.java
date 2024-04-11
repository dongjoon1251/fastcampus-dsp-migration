package fastcampus.ad.migration.application.dispatcher;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import fastcampus.ad.migration.application.legacyad.adgroup.LegacyAdGroupMigrationService;
import fastcampus.ad.migration.application.legacyad.campaign.LegacyCampaignMigrationService;
import fastcampus.ad.migration.application.legacyad.keyword.LegacyKeywordMigrationService;
import fastcampus.ad.migration.application.legacyad.user.LegacyUserMigrationService;
import fastcampus.ad.migration.application.user.MigrationUserService;
import fastcampus.ad.migration.domain.AggregateType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MigrationDispatcherTest {

  @Mock
  MigrationUserService migrationUserService;
  @Mock
  LegacyUserMigrationService legacyUserMigrationService;
  @Mock
  LegacyCampaignMigrationService legacyCampaignMigrationService;
  @Mock
  LegacyAdGroupMigrationService legacyAdGroupMigrationService;
  @Mock
  LegacyKeywordMigrationService legacyKeywordMigrationService;

  @InjectMocks
  MigrationDispatcher dispatcher;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void 사용자가_마이그레이션_동의하지_않으면_dispatch_실패() {
    when(migrationUserService.isDisagreed(1L)).thenReturn(true);

    boolean result = dispatcher.dispatch(1L, 1L, AggregateType.ADGROUP);

    assertThat(result).isFalse();
  }

  @Test
  void 사용자가_마이그레이션_동의했으면_마이그레이션_진행() {
    when(migrationUserService.isDisagreed(1L)).thenReturn(false);
    when(legacyCampaignMigrationService.migrate(1L)).thenReturn(true);

    boolean result = dispatcher.dispatch(1L, 1L, AggregateType.CAMPAIGN);

    assertThat(result).isTrue();
  }
}