package io.github.wendellvalentim.customer_address_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CustomerAddressApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerAddressApiApplication.class, args);
	}

}
