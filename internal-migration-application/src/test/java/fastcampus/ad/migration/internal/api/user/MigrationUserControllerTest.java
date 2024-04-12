package fastcampus.ad.migration.internal.api.user;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

class MigrationUserControllerTest {

  RestTemplate restTemplate = new RestTemplate();
  HttpHeaders headers = headers();

  @Test
  void agree_users() {
    for (int i = 1; i <= 100; i++) {
      var resp = restTemplate.postForEntity(
          "http://localhost:8082/migration/v1/user/" + i + "/agree", new HttpEntity<>(headers),
          MigrationUserResp.class);
      if (resp.getStatusCode().isError()) {
        System.out.println("error:" + resp.getStatusCode());
      }
    }
  }

  @NotNull
  private static HttpHeaders headers() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }
}