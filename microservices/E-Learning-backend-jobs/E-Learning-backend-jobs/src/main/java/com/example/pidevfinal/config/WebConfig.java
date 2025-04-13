package com.example.pidevfinal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("WebConfig: Mapping /attachments/** to file:/C:/pidev/attachments/");
        registry.addResourceHandler("/attachments/**")
                .addResourceLocations("file:/C:/pidev/attachments/")
                .setCachePeriod(0);
    }
}