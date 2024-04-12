package fastcampus.ad.migration.application.legacyad;

public record PageMigrationResult(Long userId, int pageNumber, int totalPages, Long totalCount,
                                  boolean isSuccess) {

}
