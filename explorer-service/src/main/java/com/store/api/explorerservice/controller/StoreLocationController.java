package com.store.api.explorerservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.api.explorerservice.dto.StoreLocation;
import com.store.api.explorerservice.repository.DriverRepository;
import com.store.api.explorerservice.repository.StoreRepository;
import com.store.api.explorerservice.security.ResponseValidation;

@RestController
public class StoreLocationController {
	@Autowired
	private KafkaTemplate<Object, Object> template;
	
	
	@PostMapping("/api/stores/location")
	public ResponseEntity<Map<String,String>> addStoreLocation(
				@RequestBody StoreLocation storeLocation
			) {
		Map<String,String> response = new HashMap<String, String>();
		ResponseValidation validate = new ResponseValidation();
		
		// Validate response
		if(validate.verifyStoreLocation(storeLocation)) {
			// Add to mysql table
			// Send to kafka to handle posting to db
			this.template.send("store_location", storeLocation);
			
		} else {
			// Error validating fields
			response.put("error", "bad data");
			return ResponseEntity.badRequest().body(response);
		}
		
		// Respond to request
		response.put("ok", "success saving data");
		return ResponseEntity.accepted().body(response);
		
	}
}
