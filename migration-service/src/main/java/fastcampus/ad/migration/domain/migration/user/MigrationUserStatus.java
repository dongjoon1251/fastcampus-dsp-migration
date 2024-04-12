package fastcampus.ad.migration.domain.migration.user;

public enum MigrationUserStatus {
  GRADUALLY_UPDATING(),
  KEYWORD_FINISHED(GRADUALLY_UPDATING),
  ADGROUP_FINISHED(KEYWORD_FINISHED),
  USER_FINISHED(ADGROUP_FINISHED),
  AGREED(USER_FINISHED);
  private final MigrationUserStatus nextStatus;

  MigrationUserStatus(MigrationUserStatus nextStatus) {
    this.nextStatus = nextStatus;
  }

  MigrationUserStatus() {
    this(null);
  }

  public MigrationUserStatus next() {
    return nextStatus;
  }
}
