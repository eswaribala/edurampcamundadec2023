package com.virtusa.bankinglocalzeebeclient.configurations;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import com.virtusa.bankinglocalzeebeclient.facades.HotelReservationFacade;

@Configuration
public class KafkaConfiguration {

	@Autowired
	private HotelReservationFacade hotelReservationFacade;
	
   @JobWorker(type = "publishReservation",autoComplete = false)
   public void publishMessageKafka(final JobClient jobClient, final ActivatedJob activatedJob){

	   Map<String,Object> map=activatedJob.getVariablesAsMap();
	  
	   MessageChannel msgChannel=hotelReservationFacade.outputChannel();
		msgChannel.send(MessageBuilder .withPayload( map.get("validation").toString())
				 .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				 .build());

	   
	   

   }
}
