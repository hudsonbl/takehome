package com.store.api.explorerservice.dto;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.store.api.explorerservice.repository.DriverRepository;
import com.store.api.explorerservice.repository.StoreRepository;

@Component
public class SearchDrivers {
	
	@Autowired
	private DriverRepository driverRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	
	public ArrayList<DriverLocation> getDriversNearStore(String StoreID, String N) throws Exception {
		Integer storeID = Integer.parseInt(StoreID);
		ArrayList<DriverLocation> closestDrivers = new ArrayList<DriverLocation>();
		try {
			Optional<StoreLocation> storeLocation = storeRepository.findById(storeID);
			if(storeLocation != null) {
				ArrayList<DriverLocation> drivers = (ArrayList<DriverLocation>) driverRepository.findAll();
				// This is where we will grab the N closest drivers to a location using coordinate geometry
				StoreLocation store = storeLocation.get();
				
				// Get a list of drivers and how far they are from the store
				ArrayList<DriverDistance> calculatedDistances = calculateDistancesFromStore(drivers, store.getLatitude(), store.getLongitude());
				
				// Sort the list Time complexity is in correlation to the sorting algorithm
				ArrayList<DriverDistance> sortedList = sortDriverDistances(calculatedDistances);
				
				// Get top N closest drivers
				for(int i = 0; i < sortedList.size(); i++) {
					if(i < Integer.parseInt(N)) {
						closestDrivers.add(sortedList.get(i).getDriver());
					}
				}
			}
		} catch(NoSuchElementException err) {
			// If no store matches ID a error is handled
			// Log errors to global logger for Developer debug
			System.out.println("\n\n Error no store by ID" );
			throw new Error();
		}
		
		return closestDrivers;
	}
	
	
	public ArrayList<DriverDistance> calculateDistancesFromStore(ArrayList<DriverLocation> drivers, Double lat, Double lon) {
		ArrayList<DriverDistance> driverDist = new ArrayList<DriverDistance>();
		for(DriverLocation driver : drivers) {
			// x2 - x1
			Double a = driver.getLatitude() - lat;
			// y2 - y1
			Double b = driver.getLongitude() - lon;
			// Distance formula
			Double crowsDistance = Math.sqrt(Math.pow(a, 2) + Math.pow(a, 2));
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
