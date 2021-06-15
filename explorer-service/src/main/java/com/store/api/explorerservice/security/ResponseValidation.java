package com.store.api.explorerservice.security;

import org.apache.commons.validator.EmailValidator;

import com.store.api.explorerservice.dto.DriverLocation;
import com.store.api.explorerservice.dto.StoreLocation;

public class ResponseValidation {
	
	public ResponseValidation() {
		
	}
	
	@SuppressWarnings("deprecation")
	public boolean verifyDriverLocation(DriverLocation driverLocation) {
		// Check if the driver ID is valid. Another commonly used validation method is a confirmation
		// Email. Since, this scenario may call for a on the fly data verification, the user
		// may already be registered. So this is where you may also check if the user is already registered
		if(!EmailValidator.getInstance().isValid(driverLocation.getDriverID())) {
			return false;
		}
		
		// Verify integrity of lat and long, could also check a map
		if(driverLocation.getLatitude().isNaN() && driverLocation.getLongitude().isNaN()) {
			return false;
		}
		
		return true;
	}

	public boolean verifyStoreLocation(StoreLocation storeLocation) {
	
		// Check store id, this can cross check other things to verify store ID such as other production DB's
		// This is just to show there was some validation on input
		if(storeLocation.getStoreID() <= 0) {
			return false;
		}
		
		// Verify integrity of lat and long: in production env may also check based on a map.
		if(storeLocation.getLatitude().isNaN() && storeLocation.getLongitude().isNaN()) {
			return false;
		}
		
		return true;
	}

}
