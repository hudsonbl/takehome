package com.store.api.explorerservice;

import java.util.ArrayList;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.util.backoff.FixedBackOff;


import com.store.api.explorerservice.dto.DriverLocation;
import com.store.api.explorerservice.dto.StoreLocation;
import com.store.api.explorerservice.repository.DriverRepository;
import com.store.api.explorerservice.repository.StoreRepository;

@SpringBootApplication
public class ExplorerServiceApplication {
	
	private final Logger logger = LoggerFactory.getLogger(ExplorerServiceApplication.class);

	private final TaskExecutor exec = new SimpleAsyncTaskExecutor();

	@Autowired
	private DriverRepository driverRepository;
	
	@Autowired
	private StoreRepository storeRepository;

	public static void main(String[] args) {
		SpringApplication.run(ExplorerServiceApplication.class, args);
	}

	/*
	 * Boot will autowire this into the container factory.
	 */
	@Bean
	public SeekToCurrentErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
		return new SeekToCurrentErrorHandler(
				new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));
	}

	@Bean
	public RecordMessageConverter converter() {
		return new StringJsonMessageConverter();
	}

	@KafkaListener(id = "DriverGroup", topics = "driver_location")
	public void listenForDrivers(DriverLocation driver) {
		
		logger.info("Received Driver: " + driver);
		if (driver.getDriverID().isEmpty()) {
			throw new RuntimeException("failed");
		}
		driverRepository.save(driver);

	}
	
	@KafkaListener(id = "StoreGroup", topics = "store_location")
	public void listenForStores(StoreLocation store) {
		
		logger.info("Received Store: " + store);
		if (store.getStoreID() <= 0) {
			throw new RuntimeException("failed");
		}
		storeRepository.save(store);
	}
	
	@KafkaListener(id = "GetDriverGroup", topics = "topic-getDrivers")
	public void listenForGetDrivers(ArrayList<DriverLocation> drivers) {
		
		logger.info("Retrieving Drivers: " + drivers);
		if (drivers.size() <= 0) {
			throw new RuntimeException("failed");
		}
	}

	@KafkaListener(id = "dltGroup", topics = "topic1.DLT")
	public void dltListen(String in) {
		
		logger.info("Received from DLT: " + in);
		this.exec.execute(() -> System.out.println("Hit Enter to terminate..."));
	}

	@Bean
	public NewTopic topic() {
		return new NewTopic("topic1", 1, (short) 1);
	}

	@Bean
	public NewTopic dlt() {
		return new NewTopic("topic1.DLT", 1, (short) 1);
	}

	@Bean
	@Profile("default") // Don't run from test(s)
	public ApplicationRunner runner() {
		
		return args -> {
			System.out.println("Hit Enter to terminate...");
			
		};
	}
}