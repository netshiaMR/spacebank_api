package org.spacebank.co;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = { SpacebankApplication.class, Jsr310JpaConverters.class })
public class SpacebankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpacebankApplication.class, args);
	}

}
