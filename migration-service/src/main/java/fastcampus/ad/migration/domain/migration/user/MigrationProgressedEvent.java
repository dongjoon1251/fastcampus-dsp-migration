package fastcampus.ad.migration.domain.migration.user;

public class MigrationProgressedEvent extends MigrationUserEvent {

  public MigrationProgressedEvent(MigrationUser migrationUser) {
    super(migrationUser);
  }
}
