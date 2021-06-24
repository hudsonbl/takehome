package com.store.api.explorerservice.controller;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class ControllerTests {

	@Autowired
	private DriverLocationController driverController;
	
	@Autowired 
	private StoreLocationController storeController;
	
	@Test
	public void contextLoads() throws Exception {
		driverController = new DriverLocationController();
		storeController = new StoreLocationController();
		assertThat(driverController).isNotNull();
		assertThat(storeController).isNotNull();
	}
}
