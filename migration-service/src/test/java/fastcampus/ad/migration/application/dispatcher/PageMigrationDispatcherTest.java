package fastcampus.ad.migration.application.dispatcher;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import fastcampus.ad.migration.application.legacyad.PageMigrationResult;
import fastcampus.ad.migration.application.legacyad.adgroup.PageLegacyAdGroupMigrationService;
import fastcampus.ad.migration.application.legacyad.keyword.PageLegacyKeywordMigrationService;
import fastcampus.ad.migration.domain.AggregateType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PageMigrationDispatcherTest {

  @Mock
  PageLegacyAdGroupMigrationService adGroupMigrationService;
  @Mock
  PageLegacyKeywordMigrationService keywordMigrationService;

  @InjectMocks
  PageMigrationDispatcher dispatcher;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void 잘못된_aggregateType이_dispatch되면_에러() {
    assertThatThrownBy(() -> dispatcher.dispatch(1L, AggregateType.CAMPAIGN))
        .isInstanceOf(PageLegacyMigrationServiceNotFoundException.class);
  }

  @ValueSource(booleans = {true, false})
  @ParameterizedTest
  void dispatch한_page_migration_service의_마이그레이션_결과_전달(boolean migrationResult) {
    when(adGroupMigrationService.migrate(1L)).thenReturn(
        new PageMigrationResult(1L, 1, 1, 1L, migrationResult));

    boolean result = dispatcher.dispatch(1L, AggregateType.ADGROUP);

    assertThat(result).isEqualTo(migrationResult);
  }
}