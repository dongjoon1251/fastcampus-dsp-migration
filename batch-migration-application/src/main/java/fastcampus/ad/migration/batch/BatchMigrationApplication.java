package fastcampus.ad.migration.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = "fastcampus.ad.migration")
public class BatchMigrationApplication {

  public static void main(String[] args) {
    SpringApplication.run(BatchMigrationApplication.class, args);
  }
}
