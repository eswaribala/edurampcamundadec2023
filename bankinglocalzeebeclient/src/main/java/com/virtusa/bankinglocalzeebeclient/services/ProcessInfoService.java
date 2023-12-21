package com.virtusa.bankinglocalzeebeclient.services;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessInfoService {
    @Autowired 
	private RestTemplate restTemplate;
    private String kcAccessToken;
	
	public void getProcessDefinitions() {
		HttpHeaders headers = new HttpHeaders();
		
		 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	       headers.add("Accept", "application/json");
	      
	       
	    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
	    map.add("client_id","camunda-identity");
	    map.add("client_secret","HnKx4i4Mv8cBRIoFyznllf5SzyqMeuyj");
	    map.add("grant_type","client_credentials");
	    
	       
	       
	       HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(map, headers);
          log.info(entity.getBody().toString());
	    ResponseEntity<JsonNode> responseEntity = null;
      try {
          responseEntity = restTemplate.postForEntity("http://localhost:18080/auth/realms/camunda-platform/protocol/openid-connect/token", entity, JsonNode.class);
          JsonNode jsonNode=responseEntity.getBody();
          JsonNode recdToken=jsonNode.get("access_token"); 
          String encoded_String = new String(recdToken.toPrettyString().getBytes(), StandardCharsets.UTF_8); 
          
          kcAccessToken=encoded_String; 
          log.info("Token"+kcAccessToken);
          headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_JSON);
          headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+kcAccessToken);
         // headers.set("Authorization", "bearer "+kcAccessToken);

         HttpEntity<String> request = new HttpEntity<String>(null,headers);

          ResponseEntity<String> responseEntityStr = restTemplate.
                  exchange("http://localhost:8081/v1/process-definitions/search", HttpMethod.GET, request,
                          String.class);
          System.out.println(responseEntityStr.getBody());
          System.out.println("token : {} Verification Passed"+kcAccessToken);
          
      } catch (Exception e) {
          e.printStackTrace();
      }
		
		
	}

	
	public String getIndexedData() {
		ResponseEntity<JsonNode> responseEntityStr = restTemplate.getForEntity("http://localhost:9200/operate-process-8.3.0_/_search?pretty", JsonNode.class);
              
        return responseEntityStr.getBody().toPrettyString();
	}
}
