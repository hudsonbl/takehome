package com.store.api.explorerservice.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "storeLocation")
public class StoreLocation {
	
	@Id // Unique ID
	private Integer storeID;
	private Double latitude;
	private Double longitude;
	
	public StoreLocation() {}
	
	public StoreLocation(Integer storeID, Double latitude, Double longitude) {
		super();
		this.storeID = storeID;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Integer getStoreID() {
		return storeID;
	}
	public void setStoreID(Integer storeID) {
		this.storeID = storeID;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
