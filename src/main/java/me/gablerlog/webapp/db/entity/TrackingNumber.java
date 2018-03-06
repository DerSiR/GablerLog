package me.gablerlog.webapp.db.entity;

import com.google.firebase.database.PropertyName;

public class TrackingNumber {
	
	private String cargoType;
	private String transporter;
	
	public TrackingNumber() {
	}
	
	public TrackingNumber(String cargoType, String transporter) {
	}
	
	@PropertyName("cargo_type")
	public String getCargoType() {
		return cargoType;
	}
	
	@PropertyName("cargo_type")
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}
	
	public String getTransporter() {
		return transporter;
	}
	
	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}
}
