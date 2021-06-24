package com.store.api.explorerservice.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.store.api.explorerservice.dto.DriverLocation;

public interface DriverRepository extends CrudRepository<DriverLocation, String> {
	
	// Query only drivers within driving distance to a store. Hard coded example value is 10 degrees of latitude. This optimizes number of drivers within range of a store
	@Query(
			value = "SELECT d.driverid, d.latitude, d.longitude FROM driver_location AS d, store_location AS s WHERE d.latitude > s.latitude - 10 AND d.latitude < s.latitude + 10 AND s.storeID=?1",
			nativeQuery = true)
	ArrayList<DriverLocation> findDriversWithinDistanceToStore(String storeID);
}
