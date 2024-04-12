package fastcampus.ad.migration.internal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fastcampus.ad.migration")
public class InternalMigrationApplication {

  public static void main(String[] args) {
    SpringApplication.run(InternalMigrationApplication.class, args);
  }
}
