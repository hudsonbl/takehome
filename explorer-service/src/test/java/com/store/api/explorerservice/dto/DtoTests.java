package com.store.api.explorerservice.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.store.api.explorerservice.dto.DriverDistance;
import com.store.api.explorerservice.dto.DriverLocation;
import com.store.api.explorerservice.dto.SearchDrivers;
import com.store.api.explorerservice.dto.StoreLocation;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
@SpringBootTest
public class DtoTests {
	
	private DriverDistance driverDistance;
	private DriverLocation driverLocation;
	private static SearchDrivers searchDrivers;
	private StoreLocation storeLocation;
	

	
	@BeforeAll
	public static void testSetup() {
		searchDrivers = new SearchDrivers();
	}
	
	@AfterAll
	public static void testCleanup() {
		
	}
	
	@Test
	public void testDriverDistanceComparatorForSort() {
		DriverLocation driver1 = new DriverLocation("test@test.com", 123.456, 123.456);
		DriverLocation driver2 = new DriverLocation("test@test.com", 123.456, 123.456);
		DriverDistance driverDist1 = new DriverDistance(driver1, 5.0);
		DriverDistance driverDist2 = new DriverDistance(driver1, 5.0);
		
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
	
	@Test
	public void testSearchDriversForCalcDistFromStore() {
		ArrayList<DriverLocation> drivers = new ArrayList<DriverLocation>();
		
		// Add list of incrementing values for lat and long
		for(int i = 0; i < 20; i++) {
			drivers.add(new DriverLocation("test_" + i + "@test.com", 123.456 + i, 123.456 + i));
		}
		
		ArrayList<DriverDistance> list = searchDrivers.calculateDistancesFromStore(drivers, 100.0, 100.0);
		for(DriverDistance testDriver : list) {
			assertThat(testDriver.getDistance() > 0);
		}
		
		ArrayList<DriverDistance> newlist = searchDrivers.sortDriverDistances(list);
		Double lastDist = 0.0;
		// If its sorted correctly each subsequent driver should be further than the last
		for(DriverDistance testDriver : newlist) {
			assertThat(testDriver.getDistance() > lastDist);
			lastDist = testDriver.getDistance();
		}
	}
}
