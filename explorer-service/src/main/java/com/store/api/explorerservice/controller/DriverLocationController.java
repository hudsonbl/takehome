package com.store.api.explorerservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.api.explorerservice.dto.DriverLocation;
import com.store.api.explorerservice.service.SearchDrivers;
import com.store.api.explorerservice.utility.RequestValidation;

@RestController
public class DriverLocationController {
	
	private Logger logger = LoggerFactory.getLogger(DriverLocationController.class);
	
	@Autowired
	private KafkaTemplate<Object, Object> template;
	
	@Autowired
	private SearchDrivers searchDrivers;
	
	
	@PostMapping("/api/drivers/location")
	public ResponseEntity<Map<String,String>> addDriverLocation(
				@RequestBody DriverLocation driverLocation
			) {
		// Create response map, this will be built upon
		Map<String,String> response = new HashMap<String, String>();
		RequestValidation validate = new RequestValidation();
		
		// Validate response
		if(validate.verifyDriverLocation(driverLocation)) {
			try {
				// Send to kafka to handle posting to db
				this.template.send("driver_location", driverLocation);
			} catch (Exception e) {
				// Error with connecting to kafka
				response.put("error", "Error connecting to kafka");
				
				logger.error("Error: " + e);
				return ResponseEntity.badRequest().body(response);
			}
		} else {
			// Error validating fields
			response.put("error", "bad data");
			
			// Log invalid request
			logger.error("Error: invalid request body");
			return ResponseEntity.badRequest().body(response);
		}
		
		
		// Respond to request successfully
		response.put("ok", "success saving data");
		return ResponseEntity.created(null).body(response);
	}
	
	@GetMapping("/api/drivers")
	public ResponseEntity<Map<String, ArrayList<DriverLocation>>> getClosestDriversToStore(
				@RequestParam String storeID,
				@RequestParam String N 
			){
		
		Map<String, ArrayList<DriverLocation>> response = new HashMap<String, ArrayList<DriverLocation>>();
		ArrayList<DriverLocation> drivers = new ArrayList<DriverLocation>();
		String numberOfClosestDrivers = N;
		
		try {
			// Get the nearest drivers to the store given a store ID
			drivers = searchDrivers.getDriversNearStore(storeID, numberOfClosestDrivers);
			
			// Checks if there was a store by the store id as well as any drivers within range to the store location
			if(drivers != null) {
				// Send result to kafka server
				try {
					// Send to kafka to handle response and retrieve get response if kafka is successful
					this.template.send("topic-getDrivers", drivers).get();
					
				} catch (Exception e) {
					// Catch kafka error
					logger.error("Error: " + e);
					
					// Return error 
					response.put("drivers", drivers);
					return ResponseEntity.internalServerError().body(response);
				}
				
				
				// Send result to REST response
				response.put("drivers", drivers);
				return ResponseEntity.accepted().body(response);
			}else {
				// Log failure from searchDrivers() 
				logger.error("Error: Problem fetching drivers from db");
				return ResponseEntity.internalServerError().body(response);
			}
			
		} catch (Exception e) {
			
			response.put("drivers", drivers);
			return ResponseEntity.internalServerError().body(response);
		}
	}
}
