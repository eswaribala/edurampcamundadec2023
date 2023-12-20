package com.virtusa.bankinglocalzeebeclient.configurations;


import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Configuration
@Slf4j
public class JobConfiguration {
	
	@Autowired
    private ZeebeClient zeebeClient;

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
    
    @JobWorker(type = "checkAge",autoComplete = false)
    public Map<String,Object> validateAge(final JobClient jobClient, ActivatedJob activatedJob){
         
    	
    	
    	Map<String,Object> response= activatedJob.getVariablesAsMap();
    	LocalDate dob=LocalDate.parse(response.get("dob").toString());
        log.info("The DOB ="+dob);
        long age=ChronoUnit.YEARS.between(dob,LocalDate.now());
        Map<String,Object> validateMap= activatedJob.getVariablesAsMap();
        validateMap.put("age", age);
        if(dob.isBefore(LocalDate.now())&&(age>=21)) {    	
        	
        	validateMap.put("isAgeValid", true);
        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(validateMap)
                .send().exceptionally(throwable -> {
                   throw new RuntimeException("Exception due to non available job");
                });
         
        }else {        
        	validateMap.put("isAgeValid", false);
        jobClient.newCompleteCommand(activatedJob.getKey())
        .variables(validateMap)
                .send().exceptionally(throwable -> {
                   throw new RuntimeException("Exception due to non available job");
                });
        }
        return validateMap;
    }
    
}
