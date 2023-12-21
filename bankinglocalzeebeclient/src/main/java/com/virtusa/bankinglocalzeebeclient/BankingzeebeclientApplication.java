package com.virtusa.bankinglocalzeebeclient;

/*
 *Note: Form should have camunda latest version for read only
 * 
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.camunda.zeebe.spring.client.annotation.Deployment;

@SpringBootApplication
@Deployment(resources ={ ("classpath*:/processes/**/*.bpmn"),("classpath*:/decisions/**/*.dmn")})
public class BankingzeebeclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingzeebeclientApplication.class, args);
    }
    @Bean
    public RestTemplate getRestTemplate() {
    	return new RestTemplate();
    }
}
