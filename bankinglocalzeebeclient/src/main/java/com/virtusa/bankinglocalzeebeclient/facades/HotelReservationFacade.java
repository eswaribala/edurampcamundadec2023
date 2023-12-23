package com.virtusa.bankinglocalzeebeclient.facades;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface HotelReservationFacade {
	String outputChannelName="reserveration-success";
	
	@Output(outputChannelName)
	MessageChannel outputChannel();

}
