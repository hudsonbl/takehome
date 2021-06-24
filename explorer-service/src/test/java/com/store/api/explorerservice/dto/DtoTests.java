package com.store.api.explorerservice.dto;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import com.store.api.explorerservice.service.DriverDistance;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class DtoTests {
	
	@Test
	public void testDriverDistanceComparatorForSort() {
		DriverLocation driver1 = new DriverLocation("test@test.com", 123.456, 123.456);
		DriverLocation driver2 = new DriverLocation("test@test.com", 123.456, 123.456);
		DriverDistance driverDist1 = new DriverDistance(driver1, 5.0);
		DriverDistance driverDist2 = new DriverDistance(driver2, 5.0);
		
		assertThat(driverDist1.equals(driverDist2) == true);
	}
	
	@Test
	public void testDriverLocationObject() {
		DriverLocation driver1 = new DriverLocation("test@test.com", 123.456, 123.456);
		assertThat(driver1.getDriverID() == "test@test.com");
		assertThat(driver1.getLatitude() == 123.456);
		assertThat(driver1.getLongitude() == 123.456);
	}
	
	@Test
	public void testStoreLocationObject() {
		StoreLocation store = new StoreLocation(11111, 123.456, 123.456);
		assertThat(store.getStoreID() == 11111);
		assertThat(store.getLatitude() == 123.456);
		assertThat(store.getLongitude() == 123.456);
	}
	
}
