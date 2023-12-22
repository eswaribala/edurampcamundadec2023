package com.virtusa.bankinglocalzeebeclient.controllers;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.bankinglocalzeebeclient.configurations.ProcessConstant;
import com.virtusa.bankinglocalzeebeclient.services.ProcessInfoService;

@RestController
@RequestMapping("/processes")
@Slf4j
public class ProcessController {

    @Autowired
    private ZeebeClient zeebeClient;
    
    @Autowired
    private ProcessInfoService processInfoService;

    @GetMapping("/")
    public ResponseEntity<?> startProcess(){

        zeebeClient.newCreateInstanceCommand().bpmnProcessId(ProcessConstant.BPMN_PROCESS_ID)
                .latestVersion()
                .send();

        return ResponseEntity.status(HttpStatus.OK).body("Process Started..."+ProcessConstant.BPMN_PROCESS_ID);



    }
    
    @GetMapping("/travel")
    public ResponseEntity<?> startTravelProcess(){

        zeebeClient.newCreateInstanceCommand().bpmnProcessId(ProcessConstant.TRAVEL_BPMN_PROCESS_ID)
                .latestVersion()
                .send();

        return ResponseEntity.status(HttpStatus.OK).body("Process Started..."+ProcessConstant.TRAVEL_BPMN_PROCESS_ID);



    }
    
    @GetMapping("/triggerautodebit")
    public ResponseEntity<?> triggerAutoDebitStartEvent(){
    	
    	HashMap<String,Boolean> money=new HashMap<>();
    	money.put("sufficientMoney", false);

        zeebeClient.newPublishMessageCommand()
        .messageName("Message_EMI_Ref")
        .correlationKey("1001")
        .messageId("Event_EMI_Message")
        .timeToLive(Duration.ofMinutes(10))
        .variables(money)
        .send()
        .exceptionally(throwable -> {
            throw new RuntimeException("Could not complete job " + zeebeClient, throwable);
        });
        log.info("Message received");
        return ResponseEntity.status(HttpStatus.OK).body("Message Delivered");


    }
  
}
