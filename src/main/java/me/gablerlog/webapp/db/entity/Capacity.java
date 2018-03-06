package me.gablerlog.webapp.db.entity;

public class Capacity {
	
	private double volume;
	private double weight;
	
	public Capacity() {
	}
	
	public Capacity(double volume, double weight) {
		this.volume = volume;
		this.weight = weight;
	}
	
	public double getVolume() {
		return volume;
	}
	
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
