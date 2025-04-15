package com.example.getway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableDiscoveryClient
public class GetwayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetwayApplication.class, args);

	}
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
		return builder.routes().route("problem",r->r.path("/problems/**").uri("lb://SERVICEPROBLEMS"))
				.route("Competition",r->r.path("/Competitions/**").uri("lb://SERVICEPROBLEMS"))

				.route("submition",r->r.path("/submitions/**").uri("lb://SERVICEPROBLEMS"))
				.route("blog",r->r.path("/blog/**").uri("lb://BLOG"))
				//.route("job",r->r.path("/job/**").uri("lb://JOB"))
				.route("job",r->r.path("/job/**").uri("lb://PIDEVFINAL"))
				.route("Subscription",r->r.path("/Subscription/**").uri("lb://Subscription"))


<<<<<<< HEAD
				.route("training",r->r.path("/e-learning/**").uri("lb://TRAINING"))
=======
				.route("training",r->r.path("/e-learning/trainings/**").uri("lb://TRAINING"))
				.route("training",r->r.path("/e-learning/evaluation/**").uri("lb://TRAINING"))
>>>>>>> Training
				.route("question",r->r.path("/question/**").uri("lb://TRAINING"))
				.route("users",r->r.path("/Users/**").uri("lb://USERSMANAGEMENTSYSTEM"))

				.build();


	}

}