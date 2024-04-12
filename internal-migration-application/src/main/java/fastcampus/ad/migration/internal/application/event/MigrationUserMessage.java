package fastcampus.ad.migration.internal.application.event;

import fastcampus.ad.migration.domain.migration.user.MigrationUserEvent;
import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;

public record MigrationUserMessage(Long userId, MigrationUserStatus status,
                                   MigrationUserStatus prevStatus) {

  public static MigrationUserMessage from(MigrationUserEvent event) {
    return new MigrationUserMessage(event.getUserId(), event.getStatus(), event.getPrevStatus());
  }
}
