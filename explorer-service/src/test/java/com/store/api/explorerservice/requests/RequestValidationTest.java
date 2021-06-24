package com.store.api.explorerservice.requests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.store.api.explorerservice.dto.DriverLocation;
import com.store.api.explorerservice.dto.StoreLocation;
import com.store.api.explorerservice.utility.RequestValidation;


public class RequestValidationTest {

	@BeforeAll
	public static void testSetup() {
		
	}
	
	@AfterAll
	public static void testCleanup() {
		
	}
	
	@Test
	public void testValidationSchemaForDriverLocation() {
		DriverLocation driverGood = new DriverLocation("good@good.com", 123.123, 123.123);
		DriverLocation driverBad = new DriverLocation("bad", 123.123, 123.123);
		
		RequestValidation validator = new RequestValidation();
		
		assertThat(validator.verifyDriverLocation(driverGood) == true);
		assertThat(validator.verifyDriverLocation(driverBad) == false);
	}
	
	@Test
	public void testValidationSchemaForStoreLocation() {
		StoreLocation storeGood = new StoreLocation(1111, 123.123, 123.123);
		StoreLocation storeBad = new StoreLocation(-1, 123.123, 123.123);
		
		RequestValidation validator = new RequestValidation();
		
		assertThat(validator.verifyStoreLocation(storeGood) == true);
		assertThat(validator.verifyStoreLocation(storeBad) == false);
	}
}
