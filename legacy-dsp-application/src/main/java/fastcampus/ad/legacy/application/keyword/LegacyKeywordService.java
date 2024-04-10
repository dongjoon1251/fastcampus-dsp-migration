package fastcampus.ad.legacy.application.keyword;

import fastcampus.ad.legacy.domain.keyword.LegacyKeyword;
import fastcampus.ad.legacy.domain.keyword.LegacyKeywordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LegacyKeywordService {

  private final LegacyKeywordRepository repository;

  @Transactional
  public LegacyKeywordResult create(
      LegacyKeywordCreateCommand command) {
    LegacyKeyword newKeyword = LegacyKeyword.of(command.text(), command.adGroupId(),
        command.userId());
    return LegacyKeywordResult.from(repository.save(newKeyword));
  }

  public LegacyKeywordResult find(Long id) {
    return LegacyKeywordResult.from(findById(id));
  }

  private LegacyKeyword findById(Long id) {
    return repository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Transactional
  public LegacyKeywordResult delete(Long id) {
    LegacyKeyword keyword = findById(id);
    keyword.delete();
    return LegacyKeywordResult.from(repository.save(keyword));
  }
}
