package fastcampus.ad.migration.application.user;

import fastcampus.ad.migration.application.legacyad.MigrationService;
import fastcampus.ad.migration.application.legacyad.user.LegacyUserMigrationService;
import fastcampus.ad.migration.domain.migration.user.MigrationUser;
import fastcampus.ad.migration.domain.migration.user.MigrationUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MigrationUserService implements MigrationService {

  private final MigrationUserRepository repository;
  private final LegacyUserMigrationService legacyUserMigrationService;

  @Transactional
  public MigrationUserResult agree(Long userId) {
    repository.findById(userId).ifPresent(migrationUser -> {
      throw new AlreadyAgreedException(String.format("User [ID: %d] already agreed", userId));
    });
    return MigrationUserResult.from(repository.save(MigrationUser.agreed(userId)));
  }

  private MigrationUser find(Long userId) {
    return repository.findById(userId)
        .orElseThrow(EntityNotFoundException::new);
  }

  public MigrationUserResult findById(Long userId) {
    return MigrationUserResult.from(find(userId));
  }

  public boolean isDisagreed(Long migrationUserId) {
    return repository.findById(migrationUserId).isEmpty();
  }

  @Transactional
  public MigrationUserResult startMigration(Long userId) throws StartMigrationFailedException {
    boolean result = migrate(userId);
    if (result) {
      return progressMigration(userId);
    }
    throw new StartMigrationFailedException();
  }

  @Override
  public boolean migrate(Long id) {
    return legacyUserMigrationService.migrate(id);
  }

  @Transactional
  public MigrationUserResult progressMigration(Long userId) {
    MigrationUser migrationUser = find(userId);
    migrationUser.progressMigration();
    return MigrationUserResult.from(repository.save(migrationUser));
  }

  @Transactional
  public MigrationUserResult retry(Long userId) {
    MigrationUser migrationUser = find(userId);
    migrationUser.retry();
    return MigrationUserResult.from(repository.save(migrationUser));
  }
}
