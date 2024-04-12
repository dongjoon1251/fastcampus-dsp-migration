package fastcampus.ad.legacy.api.campaign;

import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class LegacyCampaignControllerTest {

  RestTemplate restTemplate = new RestTemplate();
  Random random = new Random();

  @Test
  void create_many_campaign() {
    for (int i = 0; i < 1000; i++) {
      String name = "campaign" + (random.nextInt(1000) + 1);
      Long userId = random.nextLong(1000) + 1L;
      Long budget = (random.nextLong(1000) + 1L) * 100;
      var resp = restTemplate.postForEntity("http://localhost:8080/campaign/v1",
          new LegacyCampaignCreateRequest(name, userId, budget), LegacyCampaignResp.class);
      if (resp.getStatusCode().isError()) {
        System.out.println("error:" + resp.getStatusCode());
      }
      if (i % 100 == 0) {
        System.out.println("progress: " + i);
      }
    }
  }
}