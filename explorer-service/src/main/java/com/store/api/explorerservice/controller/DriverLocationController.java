package com.store.api.explorerservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.api.explorerservice.dto.DriverLocation;
import com.store.api.explorerservice.dto.SearchDrivers;
import com.store.api.explorerservice.repository.DriverRepository;
import com.store.api.explorerservice.security.ResponseValidation;

@RestController
public class DriverLocationController {
	
	@Autowired
	private KafkaTemplate<Object, Object> template;
	
	@Autowired
	private SearchDrivers searchDrivers;
	
	
	@PostMapping("/api/drivers/location")
	public ResponseEntity<Map<String,String>> addDriverLocation(
				@RequestBody DriverLocation driverLocation
			) {
		Map<String,String> response = new HashMap<String, String>();
		ResponseValidation validate = new ResponseValidation();
		
		// Validate response
		if(validate.verifyDriverLocation(driverLocation)) {
			
			// Send to kafka to handle posting to db
			this.template.send("driver_location", driverLocation);
			
		} else {
			// Error validating fields
			response.put("error", "bad data");
			return ResponseEntity.badRequest().body(response);
		}
		
		
		// Respond to request
		response.put("ok", "success saving data");
		return ResponseEntity.accepted().body(response);	
	}
	
	@GetMapping("/api/drivers")
	public ResponseEntity<Map<String, ArrayList<DriverLocation>>> getClosestDriversToStore(
				@RequestParam String StoreID,
				@RequestParam String N 
			){
		
		Map<String, ArrayList<DriverLocation>> response = new HashMap<String, ArrayList<DriverLocation>>();
		ArrayList<DriverLocation> drivers = new ArrayList<DriverLocation>();
		
		try {
			drivers = searchDrivers.getDriversNearStore(StoreID, N);
			
			// Send result to kafka server
			this.template.send("topic-getDrivers", drivers);
			
			// Send result to REST response
			response.put("drivers", drivers);
			return ResponseEntity.accepted().body(response);
		} catch (Exception e) {
			response.put("drivers", drivers);
			return ResponseEntity.internalServerError().body(response);
		}
	}
}
