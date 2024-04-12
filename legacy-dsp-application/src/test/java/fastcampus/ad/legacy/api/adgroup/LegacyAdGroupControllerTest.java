package fastcampus.ad.legacy.api.adgroup;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class LegacyAdGroupControllerTest {

  RestTemplate restTemplate = new RestTemplate();
  Random random = new Random();

  @Test
  void create_many_adgroup() throws InterruptedException {
    try (ExecutorService executorService = Executors.newFixedThreadPool(1000)) {
      AtomicInteger count = new AtomicInteger(0);
      int threadCount = 1000000 - count.get();
      CountDownLatch latch = new CountDownLatch(threadCount);
      for (int i = 0; i < threadCount; i++) {
        executorService.execute(() -> {
          createAdGroup(count);
          latch.countDown();
        });
      }
      latch.await();
    }
  }

  private void createAdGroup(AtomicInteger count) {
    try {
      String name = "adgroup" + (random.nextInt(1000) + 1);
      Long campaignId = random.nextLong(1000) + 1L;
      String linkUrl = "http://www.fastcampus.com/" + name;
      var resp = restTemplate.postForEntity("http://localhost:8080/adgroup/v1",
          new LegacyAdGroupCreateRequest(name, campaignId, linkUrl), LegacyAdGroupResp.class);
      if (resp.getStatusCode().isError()) {
        System.out.println("error:" + resp.getStatusCode());
      }
      if (count.incrementAndGet() % 100 == 0) {
        System.out.println("progress: " + count + " at " + LocalDateTime.now());
      }
    } catch (Exception e) {
      createAdGroup(count);
    }
  }
}