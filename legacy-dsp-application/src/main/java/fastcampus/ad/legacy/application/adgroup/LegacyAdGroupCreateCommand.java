package fastcampus.ad.legacy.application.adgroup;

public record LegacyAdGroupCreateCommand(String name, Long campaignId, Long userId,
                                         String linkUrl) {

}
