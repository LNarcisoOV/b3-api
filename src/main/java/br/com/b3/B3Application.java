package br.com.b3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class B3Application {

	public static void main(String[] args) {
		SpringApplication.run(B3Application.class, args);
	}

}
