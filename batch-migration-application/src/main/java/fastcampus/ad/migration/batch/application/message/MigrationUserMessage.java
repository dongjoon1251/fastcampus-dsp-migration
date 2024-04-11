package fastcampus.ad.migration.batch.application.message;

import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;

public record MigrationUserMessage(Long userId, MigrationUserStatus status) {
  
}
