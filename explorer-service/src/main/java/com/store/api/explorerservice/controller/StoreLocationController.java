package com.store.api.explorerservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.api.explorerservice.dto.StoreLocation;
import com.store.api.explorerservice.utility.RequestValidation;

@RestController
public class StoreLocationController {
	
	private Logger logger = LoggerFactory.getLogger(DriverLocationController.class);
	
	@Autowired
	private KafkaTemplate<Object, Object> template;
	
	@PostMapping("/api/stores/location")
	public ResponseEntity<Map<String,String>> addStoreLocation(
				@RequestBody StoreLocation storeLocation
			) {
		Map<String,String> response = new HashMap<String, String>();
		RequestValidation validate = new RequestValidation();
		
		// Validate response
		if(validate.verifyStoreLocation(storeLocation)) {
			try {
				// Add to mysql table
				// Send to kafka to handle posting to db and retrieve get response if kafka is successful
				this.template.send("store_location", storeLocation).get();
				
			} catch(Exception e) {
				// Kafka may be down 
				logger.error("Error: " + e);
				return ResponseEntity.internalServerError().body(response);
			} 
		} else {
			// Error validating fields
			response.put("error", "bad data");
			return ResponseEntity.badRequest().body(response);
		}
		
		// Respond to request
		response.put("ok", "success saving data");
		return ResponseEntity.created(null).body(response);
		
	}
}
