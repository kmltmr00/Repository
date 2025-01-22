package repository.repo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude=SecurityAutoConfiguration.class)
public class RepoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepoApplication.class, args);
	}

}
