package fastcampus.ad.migration.application.legacyad;

import fastcampus.ad.migration.domain.legacyad.DeletableEntity;
import fastcampus.ad.migration.domain.recentad.MigratedEntity;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public abstract class LegacyMigrationService<Legacy extends DeletableEntity, Recent extends MigratedEntity> implements
    MigrationService {

  protected CrudRepository<Legacy, Long> legacyRepository;
  protected CrudRepository<Recent, Long> recentRepository;
  protected LegacyConverter<Legacy, Recent> converter;

  public LegacyMigrationService(CrudRepository<Legacy, Long> legacyRepository,
      CrudRepository<Recent, Long> recentRepository, LegacyConverter<Legacy, Recent> converter) {
    this.legacyRepository = legacyRepository;
    this.recentRepository = recentRepository;
    this.converter = converter;
  }

  @Override
  public boolean migrate(Long id) {
    try {
      migrate(findLegacy(id));
      return true;
    } catch (RuntimeException e) {
      log.error("migration error", e);
      return false;
    }
  }

  private Legacy findLegacy(Long id) {
    return legacyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  protected void migrate(Legacy legacy) {
    if (legacy.isDeleted()) {
      deleteRecent(legacy.getId());
    } else {
      saveRecent(convert(legacy));
    }
  }

  private void deleteRecent(Long id) {
    recentRepository.findById(id).ifPresent(recent -> recentRepository.delete(recent));
  }

  private Recent convert(Legacy legacy) {
    return converter.convert(legacy);
  }

  private void saveRecent(Recent convert) {
    recentRepository.save(convert);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public boolean migrate(List<Legacy> legacies) {
    try {
      saveRecents(convert(legacies));
      return true;
    } catch (RuntimeException e) {
      log.error("list migration error", e);
      return false;
    }
  }

  private List<Recent> convert(List<Legacy> legacies) {
    return legacies.stream()
        .map(this::convert)
        .collect(Collectors.toList());
  }

  private void saveRecents(List<Recent> convert) {
    recentRepository.saveAll(convert);
  }
}
