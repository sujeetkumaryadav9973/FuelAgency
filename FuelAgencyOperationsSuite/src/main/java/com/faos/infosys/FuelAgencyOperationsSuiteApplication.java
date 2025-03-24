package com.faos.infosys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@ComponentScan(basePackages={"com.faos.*"})
@EnableJpaRepositories("com.faos.repository")
@EntityScan("com.faos.model")
@SpringBootApplication
public class FuelAgencyOperationsSuiteApplication {
	public static void main(String[] args) {
		SpringApplication.run(FuelAgencyOperationsSuiteApplication.class,args);
	}

}
