package com.hellzzangAdmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HellzzangAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(HellzzangAdminApplication.class, args);
	}

}
