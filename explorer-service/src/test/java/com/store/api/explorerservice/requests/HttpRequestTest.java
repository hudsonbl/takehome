package com.store.api.explorerservice.requests;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.store.api.explorerservice.dto.DriverLocation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void addingDriverShouldReturnBadData() throws Exception {
		Map<String, String> body = new HashMap<String, String>();
		body.put("driverID", "27.14");
		body.put("latitude", "27.14");
		body.put("longitude", "27.14");
		
		assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/api/drivers/location",
				body, Map.class)).containsKey("error");
	}
	
	@Test
	public void addingStoreShouldReturnError() throws Exception {
		Map<String, String> body = new HashMap<String, String>();
		
		assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/api/stores/location",
				body, Map.class)).containsKey("error");
	}
	
	@Test
	public void gettingDriversShouldReturnList() throws Exception {
		Map<String, String> body = new HashMap<String, String>();
		
		assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/api/drivers/location",
				body, Map.class)).containsKey("error");
	}
}
