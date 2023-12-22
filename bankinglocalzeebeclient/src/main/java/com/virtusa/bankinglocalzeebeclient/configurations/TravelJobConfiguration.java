package com.virtusa.bankinglocalzeebeclient.configurations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class TravelJobConfiguration {
	@Autowired
	private RestTemplate restTemplate;
	
	@JobWorker(type = "bookHotel",autoComplete = false)
	public void bookHotel(JobClient jobClient, ActivatedJob activatedJob) {
		
		HttpHeaders headers=new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		  //  map.add("client_id",clientId);
		  //  map.add("grant_type",grantType);
		  //  map.add("username",userName);
		  //  map.add("password",password);   
		    
		  
		HttpEntity<MultiValueMap<String, String>> request=new 
				HttpEntity<MultiValueMap<String, String>>(null,headers);
		
		ResponseEntity<LinkedHashMap[]> response= this.restTemplate
				.exchange("https://jsonplaceholder.typicode.com/users", 
						HttpMethod.GET, request, LinkedHashMap[].class);
		log.info("Received Data"+response.getBody().toString());
		
		List<String> names=new ArrayList<String>();
		for(LinkedHashMap lmap: response.getBody()) {
			log.info("Name"+lmap.get("name"));
			names.add(lmap.get("name").toString());
		}
		        
		HashMap<String, List<String>> values=new HashMap<>();
		values.put("names", names);
		
		jobClient.newCompleteCommand(activatedJob.getKey())
        .variables(values).send().exceptionally(throwable -> {
           throw new RuntimeException("Exception due to non available job");
        });
 
	}
	@JobWorker(type = "creditCardValidation",autoComplete = false)
	public void creditCardValidation(JobClient jobClient, ActivatedJob activatedJob) {
		
		Map<String,Object> data=activatedJob.getVariablesAsMap();
		List<String> names=(List<String>) data.get("names");
		names.stream().forEach(System.out::println);
		
		jobClient.newCompleteCommand(activatedJob.getKey())
        .send().exceptionally(throwable -> {
           throw new RuntimeException("Exception due to non available job");
        });
 
	}

}
