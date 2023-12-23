package com.virtusa.bankingzeebeclient;

import io.camunda.zeebe.client.api.response.ActivateJobsResponse;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.process.test.assertions.JobAssert;
import io.camunda.zeebe.process.test.inspections.InspectionUtility;
import io.camunda.zeebe.process.test.inspections.model.InspectedProcessInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.process.test.assertions.BpmnAssert;
import io.camunda.zeebe.process.test.assertions.DeploymentAssert;
import io.camunda.zeebe.process.test.assertions.ProcessInstanceAssert;
import io.camunda.zeebe.process.test.extension.ZeebeProcessTest;
import io.camunda.zeebe.process.test.filters.RecordStream;

import java.util.Optional;

@ZeebeProcessTest
public class LoanProcessTest {
	
	private ZeebeClient zeebeClient;
    private ZeebeTestEngine zeebeTestEngine;
    private RecordStream recordStream;
    
    @BeforeEach
    public void deployProcessTest() {
    	
    DeploymentEvent event=zeebeClient.newDeployResourceCommand()
    	  .addResourceFromClasspath("processes/loanprocess.bpmn")
    	  .send()
    	  .join();
    	
    	DeploymentAssert assertions = BpmnAssert.assertThat(event);
    }
    
    @Test
    public void testProcessInstance() {
    ProcessInstanceEvent event=zeebeClient.newCreateInstanceCommand()
    	       .bpmnProcessId("Process_Loan")
    	       .latestVersion()
    	       .send()
    	       .join();
    
    ProcessInstanceAssert assertions=BpmnAssert.assertThat(event);
    	       
    }

    @Test
    public void testProcessInstanceByGeneratedKey() {
        Optional<InspectedProcessInstance> firstProcessInstance =
                InspectionUtility.findProcessInstances()
                        .withParentProcessInstanceKey(2251799813685944L)
                        .withBpmnProcessId("Process_Travel")
                        .findFirstProcessInstance();
        ProcessInstanceAssert assertions = BpmnAssert.assertThat(firstProcessInstance.get());
    }

     @Test
     public void testActivatedJob(){
         ActivateJobsResponse response = zeebeClient.newActivateJobsCommand()
                 .jobType("getRandomNo")
                 .maxJobsToActivate(1)
                 .send()
                 .join();
         ActivatedJob activatedJob = response.getJobs().get(0);
         JobAssert assertions = BpmnAssert.assertThat(activatedJob);
     }

	
}
