package com.store.api.explorerservice.dto;

public class DriverDistance {
	
	private DriverLocation driver;
	private Double distance;
	
	public DriverDistance() {
		
	}
	
	public DriverDistance(DriverLocation driver, Double distance) {
		super();
		this.driver = driver;
		this.distance = distance;
	}
	
	public DriverLocation getDriver() {
		return driver;
	}
	public void setDriver(DriverLocation driver) {
		this.driver = driver;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((distance == null) ? 0 : distance.hashCode());
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
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
		DriverDistance other = (DriverDistance) obj;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		if (driver == null) {
			if (other.driver != null)
				return false;
		} else if (!driver.equals(other.driver))
			return false;
		return true;
	}
	
	
}
