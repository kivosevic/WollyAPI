package com.backend.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan("com.backend.backend.*")
@EntityScan("com.backend.backend.models")
public class WollyApiApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(WollyApiApplication.class, args);
    }
}
