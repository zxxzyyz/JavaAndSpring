package jpa.springjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
// Spring boot do this : @EnableJpaRepositories(basePackages = "jpa.springjpa.repository")
public class SpringjpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringjpaApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString());
	}

}
