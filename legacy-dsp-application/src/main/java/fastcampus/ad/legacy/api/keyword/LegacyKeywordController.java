package fastcampus.ad.legacy.api.keyword;

import fastcampus.ad.legacy.application.adgroup.LegacyAdGroupResult;
import fastcampus.ad.legacy.application.adgroup.LegacyAdGroupService;
import fastcampus.ad.legacy.application.keyword.LegacyKeywordCreateCommand;
import fastcampus.ad.legacy.application.keyword.LegacyKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keyword/v1")
public class LegacyKeywordController {

  private final LegacyKeywordService service;
  private final LegacyAdGroupService adGroupService;

  @PostMapping("")
  public LegacyKeywordResp create(@RequestBody LegacyKeywordCreateRequest req) {
    LegacyAdGroupResult adGroup = adGroupService.find(req.adGroupId());
    return LegacyKeywordResp.from(service.create(
        new LegacyKeywordCreateCommand(req.text(), adGroup.id(), adGroup.userId())));
  }

  @GetMapping("/{id}")
  public LegacyKeywordResp find(@PathVariable("id") Long id) {
    return LegacyKeywordResp.from(service.find(id));
  }

  @DeleteMapping("/{id}")
  public LegacyKeywordResp delete(@PathVariable("id") Long id) {
    return LegacyKeywordResp.from(service.delete(id));
  }
}
