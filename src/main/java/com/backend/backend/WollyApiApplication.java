package com.backend.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.backend.backend.*")
@EntityScan("com.backend.backend.models")
public class WollyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WollyApiApplication.class, args);
    }
}
