package com.store.api.explorerservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.store.api.explorerservice.dto.DriverLocation;

public interface DriverRepository extends CrudRepository<DriverLocation, String> {

}
