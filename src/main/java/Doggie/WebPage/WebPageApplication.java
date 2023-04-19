package Doggie.WebPage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class WebPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebPageApplication.class, args);
    }
}
