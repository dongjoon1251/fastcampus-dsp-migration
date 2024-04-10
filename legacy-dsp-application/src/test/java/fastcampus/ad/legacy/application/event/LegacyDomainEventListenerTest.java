package fastcampus.ad.legacy.application.event;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import fastcampus.ad.legacy.application.adgroup.LegacyAdGroupCreateCommand;
import fastcampus.ad.legacy.application.adgroup.LegacyAdGroupResult;
import fastcampus.ad.legacy.application.adgroup.LegacyAdGroupService;
import fastcampus.ad.legacy.application.campaign.LegacyCampaignCreateCommand;
import fastcampus.ad.legacy.application.campaign.LegacyCampaignResult;
import fastcampus.ad.legacy.application.campaign.LegacyCampaignService;
import fastcampus.ad.legacy.application.keyword.LegacyKeywordCreateCommand;
import fastcampus.ad.legacy.application.keyword.LegacyKeywordResult;
import fastcampus.ad.legacy.application.keyword.LegacyKeywordService;
import fastcampus.ad.legacy.application.user.LegacyUserCreateCommand;
import fastcampus.ad.legacy.application.user.LegacyUserResult;
import fastcampus.ad.legacy.application.user.LegacyUserService;
import fastcampus.ad.legacy.domain.DomainEvent;
import fastcampus.ad.legacy.domain.adgroup.event.LegacyAdGroupCreatedEvent;
import fastcampus.ad.legacy.domain.adgroup.event.LegacyAdGroupDeletedEvent;
import fastcampus.ad.legacy.domain.adgroup.event.LegacyAdGroupEvent;
import fastcampus.ad.legacy.domain.adgroup.event.LegacyAdGroupLinkUrlUpdatedEvent;
import fastcampus.ad.legacy.domain.adgroup.event.LegacyAdGroupNameUpdatedEvent;
import fastcampus.ad.legacy.domain.campaign.event.LegacyCampaignBudgetUpdatedEvent;
import fastcampus.ad.legacy.domain.campaign.event.LegacyCampaignCreatedEvent;
import fastcampus.ad.legacy.domain.campaign.event.LegacyCampaignDeletedEvent;
import fastcampus.ad.legacy.domain.campaign.event.LegacyCampaignEvent;
import fastcampus.ad.legacy.domain.campaign.event.LegacyCampaignNameUpdatedEvent;
import fastcampus.ad.legacy.domain.keyword.event.LegacyKeywordCreatedEvent;
import fastcampus.ad.legacy.domain.keyword.event.LegacyKeywordDeletedEvent;
import fastcampus.ad.legacy.domain.keyword.event.LegacyKeywordEvent;
import fastcampus.ad.legacy.domain.user.event.LegacyUserCreatedEvent;
import fastcampus.ad.legacy.domain.user.event.LegacyUserDeletedEvent;
import fastcampus.ad.legacy.domain.user.event.LegacyUserEvent;
import fastcampus.ad.legacy.domain.user.event.LegacyUserNameUpdatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class LegacyDomainEventListenerTest {

  @Autowired
  LegacyKeywordService keywordService;
  @Autowired
  LegacyAdGroupService adGroupService;
  @Autowired
  LegacyCampaignService campaignService;
  @Autowired
  LegacyUserService userService;
  @MockBean
  LegacyDomainEventListener eventListener;

  @Test
  void userEvents() {
    LegacyUserCreateCommand command = new LegacyUserCreateCommand("사용자");

    LegacyUserResult result = userService.create(command);
    userService.updateName(result.id(), "사용자1");
    userService.delete(result.id());

    assertAll(
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyUserCreatedEvent.class)),
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyUserNameUpdatedEvent.class)),
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyUserDeletedEvent.class)),
        () -> verify(eventListener, times(3)).handleEvent(any(LegacyUserEvent.class)),
        () -> verify(eventListener, times(3)).handleEvent(any(DomainEvent.class)));
  }

  @Test
  void campaignEvents() {
    LegacyCampaignCreateCommand command = new LegacyCampaignCreateCommand("캠페인", 1L, 200L);

    LegacyCampaignResult result = campaignService.create(command);
    campaignService.updateName(result.id(), "캠페인1");
    campaignService.updateBudget(result.id(), 1000L);
    campaignService.delete(result.id());

    assertAll(
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyCampaignCreatedEvent.class)),
        () -> verify(eventListener, times(1)).handleEvent(
            any(LegacyCampaignNameUpdatedEvent.class)),
        () -> verify(eventListener, times(1)).handleEvent(
            any(LegacyCampaignBudgetUpdatedEvent.class)),
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyCampaignDeletedEvent.class)),
        () -> verify(eventListener, times(4)).handleEvent(any(LegacyCampaignEvent.class)),
        () -> verify(eventListener, times(4)).handleEvent(any(DomainEvent.class)));
  }

  @Test
  void adGroupEvents() {
    LegacyAdGroupCreateCommand command = new LegacyAdGroupCreateCommand("광고그룹", 1L, 1L,
        "https://www.fastcampus.com");

    LegacyAdGroupResult result = adGroupService.create(command);
    adGroupService.updateName(result.id(), "광고그룹1");
    adGroupService.updateLinkUrl(result.id(), "https://www.fastcampus1.com");
    adGroupService.delete(result.id());

    assertAll(
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyAdGroupCreatedEvent.class)),
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyAdGroupNameUpdatedEvent.class)),
        () -> verify(eventListener, times(1)).handleEvent(
            any(LegacyAdGroupLinkUrlUpdatedEvent.class)),
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyAdGroupDeletedEvent.class)),
        () -> verify(eventListener, times(4)).handleEvent(any(LegacyAdGroupEvent.class)),
        () -> verify(eventListener, times(4)).handleEvent(any(DomainEvent.class)));
  }

  @Test
  void keywordEvents() {
    LegacyKeywordCreateCommand command = new LegacyKeywordCreateCommand("keyword", 1L, 1L);

    LegacyKeywordResult result = keywordService.create(command);
    keywordService.delete(result.id());

    assertAll(
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyKeywordCreatedEvent.class)),
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyKeywordDeletedEvent.class)),
        () -> verify(eventListener, times(2)).handleEvent(any(LegacyKeywordEvent.class)),
        () -> verify(eventListener, times(2)).handleEvent(any(DomainEvent.class)));
  }

}