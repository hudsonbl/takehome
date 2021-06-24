package com.store.api.explorerservice.service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.store.api.explorerservice.dto.DriverLocation;
import com.store.api.explorerservice.dto.StoreLocation;
import com.store.api.explorerservice.repository.DriverRepository;
import com.store.api.explorerservice.repository.StoreRepository;

@Component
public class SearchDrivers {
	
	private Logger logger = LoggerFactory.getLogger(SearchDrivers.class);
	
	@Autowired
	private DriverRepository driverRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	
	public ArrayList<DriverLocation> getDriversNearStore(String storeID, String numberOfClosestDrivers) throws Exception {
		
		ArrayList<DriverLocation> closestDrivers = new ArrayList<DriverLocation>();
		try {
			
			// Ensure that the store is within the Database
			Optional<StoreLocation> storeLocation = storeRepository.findById(Integer.parseInt(storeID));
			
			// If store is found then this will eval to true
			if(storeLocation.isPresent()) {
				// Optimize MySQL query to search only for drivers within appropriate range of the store say its +- 10 longitude degrees
				// This could be enhanced further to include latitude as well but longitude is used for simplicity
				ArrayList<DriverLocation> drivers = driverRepository.findDriversWithinDistanceToStore(storeID);
				//ArrayList<DriverLocation> drivers = (ArrayList<DriverLocation>) driverRepository.findAll();
				
				// This is where we will grab the N closest drivers to a location using coordinate geometry
				StoreLocation store = storeLocation.get();
				
				// Get a list of drivers and how far they are from the store
				ArrayList<DriverDistance> calculatedDistances = calculateDistancesFromStore(drivers, store.getLatitude(), store.getLongitude());
				
				// Sort the list Time complexity is in correlation to the sorting algorithm
				ArrayList<DriverDistance> sortedList = sortDriverDistances(calculatedDistances);
			
				
				// Get top N closest drivers
				for(int i = 0; i < sortedList.size(); i++) {
				
					if(i < Integer.parseInt(numberOfClosestDrivers)) {
						closestDrivers.add(sortedList.get(i).getDriver());
					}else {
						// Quit execution of aggregating list
						break;
					}
				}
			} else {
				// This triggers if storeLocation is not present()
				logger.error("Error: no store was found with ID");
				return null;
			}
		} catch(NoSuchElementException err) {
			// If no store matches ID a error is handled
			// Log errors to global logger for Developer debug
			logger.error("Error: There was an error connecting to the DB");
			return null;
		}
		
		return closestDrivers;
	}
	
	
	public ArrayList<DriverDistance> calculateDistancesFromStore(ArrayList<DriverLocation> drivers, Double storeLatitude, Double storeLongitude) {
		ArrayList<DriverDistance> driverDist = new ArrayList<DriverDistance>();
		for(DriverLocation driver : drivers) {
			// x2 - x1
			Double x = driver.getLatitude() - storeLatitude;
			// y2 - y1
			Double y = driver.getLongitude() - storeLongitude;
			// Distance formula
			Double crowsDistance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
			
			// Save distance and driver as a key pair with the DriverDistance object. Used for sorting later.
			DriverDistance driverBody = new DriverDistance( 
						driver,
						crowsDistance 
					);
			
			driverDist.add(driverBody);
		}
		return driverDist;
	}
	
	// Sort a list O(N log(N)) according to https://www.baeldung.com/arrays-sortobject-vs-sortint
	public ArrayList<DriverDistance> sortDriverDistances(ArrayList<DriverDistance> list) {
		
		list.sort((o1, o2) 
						-> o1.getDistance().compareTo(o2.getDistance()));
		
		return list;
	}
	
}
