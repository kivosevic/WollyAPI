package rs.vegait.wolly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan("rs.vegait.wolly.*")
@EntityScan("rs.vegait.wolly.models")
public class WollyApiApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(WollyApiApplication.class, args);
    }
}
