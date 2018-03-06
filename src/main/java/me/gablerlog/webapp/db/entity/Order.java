package me.gablerlog.webapp.db.entity;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import me.gablerlog.webapp.db.HasKey;

@IgnoreExtraProperties
public class Order implements HasKey {
	
	private String trackingNumber;
	
	private Location origin;
	private Location destination;
	private Capacity bulk;
	@PropertyName("latest_delivery")
	private long	 latestDelivery;
	@PropertyName("expected_duration")
	private long	 expectedDelivery;
	private String	 state;
	
	public Order() {
	}
	
	public Order(Location origin, Location destination, Capacity bulk, long latestDelivery, long expectedDelivery, String state) {
		this.origin = origin;
		this.destination = destination;
		this.bulk = bulk;
		this.latestDelivery = latestDelivery;
		this.expectedDelivery = expectedDelivery;
		this.state = state;
	}
	
	@Override
	public String getKey() {
		return trackingNumber;
	}
	
	@Override
	public void setKey(String key) {
		trackingNumber = key;
	}
	
	public Location getOrigin() {
		return origin;
	}
	
	public void setOrigin(Location origin) {
		this.origin = origin;
	}
	
	public Location getDestination() {
		return destination;
	}
	
	public void setDestination(Location destination) {
		this.destination = destination;
	}
	
	public Capacity getBulk() {
		return bulk;
	}
	
	public void setBulk(Capacity bulk) {
		this.bulk = bulk;
	}
	
	public long getLatestDelivery() {
		return latestDelivery;
	}
	
	public void setLatestDelivery(long latestDelivery) {
		this.latestDelivery = latestDelivery;
	}
	
	public long getExpectedDuration() {
		return expectedDelivery;
	}
	
	public void setExpectedDuration(long expectedDelivery) {
		this.expectedDelivery = expectedDelivery;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
}
