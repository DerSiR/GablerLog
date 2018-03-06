package me.gablerlog.webapp.db.entity;

import me.gablerlog.webapp.db.HasKey;

public class Category implements HasKey {
	
	private String key;
	
	private String title;
	
	public Category() {
	}
	
	public Category(String title) {
		this.title = title;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	@Override
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
