package me.gablerlog.webapp.db.entity;

import com.google.firebase.database.IgnoreExtraProperties;

import me.gablerlog.webapp.db.HasKey;

@IgnoreExtraProperties
public class Transporter implements HasKey {
	
	private String uuid;
	
	private String	 numberplate;
	private Capacity capacity;
	private Location location;
	
	public Transporter() {
	}
	
	public Transporter(String numberplate, Capacity capacity, Location location) {
		this.numberplate = numberplate;
		this.capacity = capacity;
		this.location = location;
	}
	
	@Override
	public String getKey() {
		return uuid;
	}
	
	@Override
	public void setKey(String key) {
		uuid = key;
	}
	
	public String getNumberplate() {
		return numberplate;
	}
	
	public void setNumberplate(String numberplate) {
		this.numberplate = numberplate;
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
