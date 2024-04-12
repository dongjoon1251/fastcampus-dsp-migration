package fastcampus.ad.migration.internal.api.user;

import fastcampus.ad.migration.application.user.MigrationUserResult;
import fastcampus.ad.migration.application.user.MigrationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/migration/v1/user")
public class MigrationUserController {

  private final MigrationUserService migrationUserService;

  @PostMapping("/{userId}/agree")
  public MigrationUserResp agreeMigration(@PathVariable Long userId) {
    MigrationUserResult result = migrationUserService.agree(userId);
    return new MigrationUserResp(result.id(), result.status(),
        result.agreedDate(), result.updateDate());
  }

  @GetMapping("/{userId}")
  public MigrationUserResp findMigrationUser(@PathVariable Long userId) {
    MigrationUserResult result = migrationUserService.findById(userId);
    return new MigrationUserResp(result.id(), result.status(),
        result.agreedDate(), result.updateDate());
  }

  @PutMapping("/{userId}/retry")
  public MigrationUserResp retryMigrationByUser(@PathVariable Long userId) {
    MigrationUserResult result = migrationUserService.retry(userId);
    return new MigrationUserResp(result.id(), result.status(),
        result.agreedDate(), result.updateDate());
  }
}
