package esprit.subscription;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
<<<<<<< HEAD
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
=======
>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce

@EnableDiscoveryClient
@SpringBootApplication
public class SubscriptionApplication {
	public static void main(String[] args) {
		SpringApplication.run(SubscriptionApplication.class, args);
	}
<<<<<<< HEAD

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}


}
=======
}



>>>>>>> 4729e87dcbaa403d121388ce801cf78054c376ce
