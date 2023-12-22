package com.virtusa.bankinglocalzeebeclient.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Service

public class ProcessInfoService {
    @Autowired 
	private RestTemplate restTemplate;
    private String kcAccessToken;
	
	public void getProcessDefinitions() {
		
	    ResponseEntity<JsonNode> responseEntity = null;
	    HttpHeaders headers = new HttpHeaders();
      try {
          
        
          headers.setContentType(MediaType.APPLICATION_JSON);
        
         // headers.set("Authorization", "bearer "+kcAccessToken);

         HttpEntity<String> request = new HttpEntity<String>(null,headers);

       responseEntity = restTemplate.
                  exchange("http://host.docker.internal:8081/v1/process-definitions/search", HttpMethod.GET, request,
                          JsonNode.class);
          System.out.println(responseEntity.getBody());
          
          
      } catch (Exception e) {
          e.printStackTrace();
      }
		
		
	}

	
	public String getIndexedData() {
		ResponseEntity<JsonNode> responseEntityStr = restTemplate.getForEntity("http://localhost:9200/operate-process-8.3.0_/_search?pretty", JsonNode.class);
              
        return responseEntityStr.getBody().toPrettyString();
	}
}
