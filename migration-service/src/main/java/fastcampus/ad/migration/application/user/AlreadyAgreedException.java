package fastcampus.ad.migration.application.user;

public class AlreadyAgreedException extends RuntimeException {

  public AlreadyAgreedException(String message) {
    super(message);
  }
}
