package com.store.api.explorerservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.store.api.explorerservice.dto.StoreLocation;

public interface StoreRepository extends CrudRepository<StoreLocation, Integer>{

}
