package me.gablerlog.webapp.db.entity;

public class Capacity {
	
	private double volume;
	private int	   weight;
	
	public Capacity() {
	}
	
	public Capacity(double volume, int weight) {
		this.volume = volume;
		this.weight = weight;
	}
	
	public double getVolume() {
		return volume;
	}
	
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
}
