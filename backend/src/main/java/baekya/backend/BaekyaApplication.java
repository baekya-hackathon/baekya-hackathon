package baekya.backend;

import baekya.backend.common.config.PerplexityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PerplexityProperties.class)
public class BaekyaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaekyaApplication.class, args);
    }

}
