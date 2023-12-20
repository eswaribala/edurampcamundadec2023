package com.virtusa.bankinglocalzeebeclient.configurations;


import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Configuration
@Slf4j
public class JobConfiguration {
    //bean
    @JobWorker(type = "getRandomNo",autoComplete = false)
    public HashMap<String,Object> applicationNoGenerator(final JobClient jobClient, ActivatedJob activatedJob){

        log.info("Jobclient...."+activatedJob.getKey());
        //generate loan application no
        HashMap<String,Object> map =new HashMap<>();
        map.put("applicationNo", new Random().nextInt(1000000));
        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(map).send().exceptionally(throwable -> {
                   throw new RuntimeException("Exception due to non available job");
                });
          return map;

    }

    
    @JobWorker(type = "showApplicationNo",autoComplete = false)
    public void showApplicationNo(final JobClient jobClient, ActivatedJob activatedJob){
          
    	Map<String,Object> response= activatedJob.getVariablesAsMap();
        log.info("The Generated ApplicationNo="+response.get("applicationNo"));
    	
        jobClient.newCompleteCommand(activatedJob.getKey())
                .send().exceptionally(throwable -> {
                   throw new RuntimeException("Exception due to non available job");
                });
         

    }
    
    
    
}
