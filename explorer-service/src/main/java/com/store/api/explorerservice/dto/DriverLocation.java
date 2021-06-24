package com.store.api.explorerservice.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "driverLocation")
public class DriverLocation {
	
	@Id // Unique ID
	private String driverID;
	
	private Double latitude;
	private Double longitude;
	
	public DriverLocation() {
		
	}
	
	public DriverLocation(String driverID, Double latitude, Double longitude) {
		super();
		this.driverID = driverID;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	// Getters and setters for Driver Location Post Body
	public String getDriverID() {
		return driverID;
	}
	public void setDriverID(String driverID) {
		this.driverID = driverID;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driverID == null) ? 0 : driverID.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriverLocation other = (DriverLocation) obj;
		if (driverID == null) {
			if (other.driverID != null)
				return false;
		} else if (!driverID.equals(other.driverID))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		return true;
	}
	
	
}
