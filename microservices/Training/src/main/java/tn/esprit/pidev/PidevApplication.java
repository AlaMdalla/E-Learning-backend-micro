package tn.esprit.pidev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PidevApplication {

    public static void main(String[] args) {
        SpringApplication.run(PidevApplication.class, args);
    }

}
