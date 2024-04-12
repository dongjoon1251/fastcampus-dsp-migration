package fastcampus.ad.migration.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fastcampus.ad.migration")
public class BatchMigrationApplication {

  public static void main(String[] args) {
    SpringApplication.run(BatchMigrationApplication.class, args);
  }
}
