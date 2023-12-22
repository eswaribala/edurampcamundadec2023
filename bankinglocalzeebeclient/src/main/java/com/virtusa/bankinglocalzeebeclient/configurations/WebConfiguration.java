package com.virtusa.bankinglocalzeebeclient.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedOrigins("*")
                .allowedHeaders("Content_Type", "Authorization")
                .allowCredentials(true)
                .exposedHeaders("abc")
                .maxAge(36L);

        registry.addMapping("*")
                .allowedMethods("GET")
                .allowedOrigins("https://stackoverflow.com")
                .allowedHeaders("X-Request-Id", "Authorization")
                .allowCredentials(false)
                .exposedHeaders("abc")
                .maxAge(36L);

    }
}
