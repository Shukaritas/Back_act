package com.agroapp.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AgroAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgroAppApplication.class, args);
    }

}
