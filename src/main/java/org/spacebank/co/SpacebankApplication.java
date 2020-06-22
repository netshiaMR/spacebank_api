package org.spacebank.co;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackageClasses = { SpacebankApplication.class, Jsr310JpaConverters.class })
@ComponentScan
public class SpacebankApplication {
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	@org.springframework.context.annotation.Bean
    public org.springframework.web.context.request.RequestContextListener requestContextListener() {
        return new org.springframework.web.context.request.RequestContextListener();
    }

	public static void main(String[] args) {
		SpringApplication.run(SpacebankApplication.class, args);
	}

}
