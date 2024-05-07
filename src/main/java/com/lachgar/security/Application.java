package com.lachgar.security;

import com.lachgar.security.entity.RoleEntity;
import com.lachgar.security.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository){
		return  args -> {
			if (roleRepository.findByName("USER").isEmpty()){
				roleRepository.save(
						RoleEntity.builder().name("USER").build()
				);
			}
		};
	}

}
