package me.gablerlog.webapp.db.entity;

import java.util.LinkedList;

import me.gablerlog.webapp.db.HasKey;

public class Route implements HasKey {
	
	private String transporter;
	
	private LinkedList<Order> orders;
	
	public Route(LinkedList<Order> orders) {
		this.orders = orders;
	}
	
	@Override
	public String getKey() {
		return transporter;
	}
	
	@Override
	public void setKey(String key) {
		transporter = key;
	}
	
	public LinkedList<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(LinkedList<Order> orders) {
		this.orders = orders;
	}
}
