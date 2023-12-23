package com.virtusa.bankingzeebeclient;

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

@ZeebeProcessTest
public class LoanProcessTest {
	
	private ZeebeClient zeebeClient;
    private ZeebeTestEngine zeebeTestEngine;
    private RecordStream recordStream;
    
    @BeforeEach
    public void deployProcessTest() {
    	
    DeploymentEvent event=zeebeClient.newDeployResourceCommand()
    	  .addResourceFromClasspath("/processes/loanprocess.bpmn")
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
    
    
	
}
