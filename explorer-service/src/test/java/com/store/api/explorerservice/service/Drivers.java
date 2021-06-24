package com.store.api.explorerservice.service;

import java.util.ArrayList;

import com.store.api.explorerservice.dto.DriverLocation;

// This class is used for testing the get response for getting closest drivers
public class Drivers {
	
	private ArrayList<DriverLocation> drivers;

	public Drivers(ArrayList<DriverLocation> drivers) {
		super();
		this.drivers = drivers;
	}

	public ArrayList<DriverLocation> getDrivers() {
		return drivers;
	}

	public void setDrivers(ArrayList<DriverLocation> drivers) {
		this.drivers = drivers;
	}

	
}
