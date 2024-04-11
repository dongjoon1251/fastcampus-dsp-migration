package fastcampus.ad.migration.internal.api.user;

import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;
import java.time.LocalDateTime;

public record MigrationUserResp(Long id, MigrationUserStatus status, LocalDateTime agreedDate,
                                LocalDateTime updateDate) {

}
