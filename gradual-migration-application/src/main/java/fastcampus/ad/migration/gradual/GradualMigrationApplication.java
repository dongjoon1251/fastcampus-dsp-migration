package fastcampus.ad.migration.gradual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fastcampus.ad.migration")
public class GradualMigrationApplication {

  public static void main(String[] args) {
    SpringApplication.run(GradualMigrationApplication.class, args);
  }
}
