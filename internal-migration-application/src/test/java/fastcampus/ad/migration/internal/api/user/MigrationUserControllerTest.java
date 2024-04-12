package fastcampus.ad.migration.internal.api.user;

import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


  @Test
  void retry_not_finished_user() {
    for (int i = 1; i <= 100; i++) {
      var user = find(i);
      if (isNotFinished(user)) {
        retry(i);
      }
    }
  }

  @NotNull
  private ResponseEntity<MigrationUserResp> find(int i) {
    return restTemplate.getForEntity("http://localhost:8082/migration/v1/user/" + i,
        MigrationUserResp.class);
  }

  private static boolean isNotFinished(ResponseEntity<MigrationUserResp> resp) {
    return resp.getStatusCode().is2xxSuccessful() &&
        !List.of(MigrationUserStatus.GRADUALLY_UPDATING)
            .contains(Objects.requireNonNull(resp.getBody()).status());
  }

  private void retry(int userId) {
    var resp = restTemplate.exchange("http://localhost:8082/migration/v1/user/" + userId + "/retry",
        HttpMethod.PUT, new HttpEntity<>(headers), MigrationUserResp.class);
    if (resp.getStatusCode().isError()) {
      System.out.println("error:" + resp.getStatusCode());
    }
  }
}