package fastcampus.ad.legacy.api.keyword;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class LegacyKeywordControllerTest {

  RestTemplate restTemplate = new RestTemplate();
  Random random = new Random();

  @Test
  void create_many_keywords() throws InterruptedException {
    for (int i = 1; i <= 20; i++) {
      create_many_keyword(i);
    }
  }

  void create_many_keyword(int step) throws InterruptedException {
    try (ExecutorService executorService = Executors.newFixedThreadPool(1000)) {
      AtomicInteger count = new AtomicInteger(0);
      int threadCount = 5000000 - count.get();
      CountDownLatch latch = new CountDownLatch(threadCount);
      for (int i = 0; i < threadCount; i++) {
        executorService.execute(() -> {
          createKeyword(count, step);
          latch.countDown();
        });
      }
      latch.await();
    }
  }

  private void createKeyword(AtomicInteger count, int step) {
    try {
      String text = "keyword" + (random.nextInt(100) + 1);
      Long adGroupId = random.nextLong(1000000) + 1L;
      var resp = restTemplate.postForEntity("http://localhost:8080/keyword/v1",
          new LegacyKeywordCreateRequest(text, adGroupId), LegacyKeywordResp.class);
      if (resp.getStatusCode().isError()) {
        System.out.println("error:" + resp.getStatusCode());
      }
      if (count.incrementAndGet() % 100 == 0) {
        System.out.println("[" + step + "] progress: " + count + " at " + LocalDateTime.now());
      }
    } catch (Exception e) {
      createKeyword(count, step);
    }
  }
}