package com.italo.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyApplication.class, args);
	}

}
