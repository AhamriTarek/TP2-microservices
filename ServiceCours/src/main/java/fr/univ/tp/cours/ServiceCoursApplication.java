package fr.univ.tp.cours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class ServiceCoursApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCoursApplication.class, args);
    }

    /**
     * RestTemplate partagé avec des timeouts :
     * si ServiceEtudiants ne répond pas, on n'attend pas indéfiniment (résilience).
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(2))
                .setReadTimeout(Duration.ofSeconds(2))
                .build();
    }
}
