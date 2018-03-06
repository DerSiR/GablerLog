package me.gablerlog.webapp.db.entity;

import com.google.firebase.database.IgnoreExtraProperties;

import me.gablerlog.webapp.db.HasKey;

@IgnoreExtraProperties
public class Transporter implements HasKey {
	
	private String key;
	
	private String	 numberplate;
	private String	 cargoType;
	private Capacity capacity;
	private Location location;
	
	public Transporter() {
	}
	
	public Transporter(String numberplate, String cargoType, Capacity capacity, Location location) {
		this.numberplate = numberplate;
		this.cargoType = cargoType;
		this.capacity = capacity;
		this.location = location;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	@Override
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getNumberplate() {
		return numberplate;
	}
	
	public void setNumberplate(String numberplate) {
		this.numberplate = numberplate;
	}
	
	public String getCargoType() {
		return cargoType;
	}
	
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}
	
	public Capacity getCapacity() {
		return capacity;
	}
	
	public void setCapacity(Capacity capacity) {
		this.capacity = capacity;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
}
