package me.gablerlog.webapp.db.entity;

import java.util.ArrayList;

import com.google.firebase.database.IgnoreExtraProperties;

import me.gablerlog.webapp.db.HasKey;

@SuppressWarnings("serial")
@IgnoreExtraProperties
public class CargoType extends ArrayList<String> implements HasKey {
	
	private String key;
	
	public CargoType() {
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	@Override
	public void setKey(String key) {
		this.key = key;
	}
}
