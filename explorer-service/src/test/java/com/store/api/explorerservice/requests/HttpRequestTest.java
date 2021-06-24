package com.store.api.explorerservice.requests;

import org.json.JSONException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


import com.store.api.explorerservice.ExplorerServiceApplication;
import com.store.api.explorerservice.dto.DriverLocation;
import com.store.api.explorerservice.dto.StoreLocation;
import com.store.api.explorerservice.service.Drivers;

import com.store.api.explorerservice.service.Stores;

import static org.junit.Assert.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=ExplorerServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate = new TestRestTemplate();
	
	
	HttpHeaders headers = new HttpHeaders();
	
	
	@Test
	public void retrieveDriversNearStore() throws JSONException {
		HttpEntity<Drivers> entity = new HttpEntity<Drivers>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/drivers?storeID=1234&N=4"),
				HttpMethod.GET, entity, String.class);
		
		String expected = "{\"drivers\":[{\"driverID\":\"goodemail@gmail.com\",\"latitude\":12.32,\"longitude\":123.32},{\"driverID\":\"user_1_124@gmail.com\",\"latitude\":10.0,\"longitude\":123.0},{\"driverID\":\"user_1_125@gmail.com\",\"latitude\":10.0,\"longitude\":124.0},{\"driverID\":\"user_1_123@gmail.com\",\"latitude\":10.0,\"longitude\":122.0}]}";
		JSONAssert.assertEquals(expected, response.getBody(), true);
	}
	
	@Test
	public void retrieveDriversNearStoreAndFail() throws JSONException {
		HttpEntity<Drivers> entity = new HttpEntity<Drivers>(null, headers);
		// Check for a store that isnt there
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/drivers?storeID=15&N=4"),
				HttpMethod.GET, entity, String.class);
		
		assertEquals(500, response.getStatusCodeValue());
	}
	
	@Test
	public void addingDriverShouldReturnBadData() throws JSONException {
		
		DriverLocation driver = new DriverLocation("badDatagmail.com", 12.32, 123.32);
		HttpEntity<DriverLocation> entity = new HttpEntity<DriverLocation>(driver, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/drivers/location"),
				HttpMethod.POST, entity, String.class);
		
		String expected = "{\"error\":\"bad data\"}";
		JSONAssert.assertEquals(expected, response.getBody(), true);
	}
	
	@Test
	public void addingDriverShouldReturnGoodData() throws JSONException {
		
		DriverLocation driver = new DriverLocation("goodemail@gmail.com", 12.32, 123.32);
		HttpEntity<DriverLocation> entity = new HttpEntity<DriverLocation>(driver, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/drivers/location"),
				HttpMethod.POST, entity, String.class);
		
		String expected = "{\"ok\":\"success saving data\"}";
		
		JSONAssert.assertEquals(expected, response.getBody(), true);
	}
	
	@Test
	public void addingStoreShouldReturnError() throws JSONException {
		Stores store = new Stores("Bad", 12.32, 123.32);
		HttpEntity<Stores> entity = new HttpEntity<Stores>(store, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/stores/location"),
				HttpMethod.POST, entity, String.class);
		
		assertEquals(400, response.getStatusCodeValue());
	}
	
	@Test
	public void addingStoreShouldReturnCreated() throws JSONException {
		StoreLocation store = new StoreLocation(1234, 12.32, 123.32);
		HttpEntity<StoreLocation> entity = new HttpEntity<StoreLocation>(store, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/stores/location"),
				HttpMethod.POST, entity, String.class);
		
		assertEquals(201, response.getStatusCodeValue());
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
