package bookie_world;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookieWorldBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookieWorldBackendApplication.class, args);
	}

}
