package bookie_world;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import bookie_world.role.Role;
import bookie_world.role.RoleRepository;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BookieWorldBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookieWorldBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		String defaultRole = "USER";
		return args -> {
			if (roleRepository.findByName(defaultRole).isEmpty()) {
				roleRepository.save(Role.builder().name(defaultRole).build());
			}
		};
	}

}
