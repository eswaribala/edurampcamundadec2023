package com.virtusa.bankinglocalzeebeclient.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.bankinglocalzeebeclient.services.ProcessInfoService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ProcessDefController {
    @Autowired 
	private ProcessInfoService processInfoService;
	
	 @GetMapping("/keycloak")
	  void handleToken() throws IOException {
	     this.processInfoService.getProcessDefinitions();
	  }
	 @GetMapping("/elastic")
	  ResponseEntity<String> fetchData() throws IOException {
	     return ResponseEntity.status(HttpStatus.OK).body(processInfoService.getIndexedData());
	  }
}
