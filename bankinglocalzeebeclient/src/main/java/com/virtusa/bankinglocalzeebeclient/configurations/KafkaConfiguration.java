package com.virtusa.bankinglocalzeebeclient.configurations;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

   @JobWorker(type = "publishReservation",autoComplete = false)
   public void publishMessageKafka(final JobClient jobClient, final ActivatedJob activatedJob){



   }
}
