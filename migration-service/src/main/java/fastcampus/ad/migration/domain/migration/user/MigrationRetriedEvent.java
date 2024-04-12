package fastcampus.ad.migration.domain.migration.user;

public class MigrationRetriedEvent extends MigrationUserEvent {

  public MigrationRetriedEvent(MigrationUser migrationUser) {
    super(migrationUser);
  }
}
