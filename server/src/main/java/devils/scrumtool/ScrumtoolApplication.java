package devils.scrumtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// @EnableRepositories(basePackages = "devils.scrumtool.repositories")
public class ScrumtoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrumtoolApplication.class, args);
    }
}
