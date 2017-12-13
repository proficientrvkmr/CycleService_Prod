package com.app.geo;

public class Store {

	private String bikeName;
	private String make;
	private String model;
	private String trim;
	private String address;
	private Location location;
		
	public String getBikeName() {
		return bikeName;
	}
	public void setBikeName(String bikeName) {
		this.bikeName = bikeName;
	}
	
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

	public String getTrim() {
		return trim;
	}
	public void setTrim(String trim) {
		this.trim = trim;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
}
