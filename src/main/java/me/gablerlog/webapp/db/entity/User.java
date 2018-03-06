package me.gablerlog.webapp.db.entity;

import java.util.Map;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import me.gablerlog.webapp.db.HasKey;

@IgnoreExtraProperties
public class User implements HasKey {
	
	private String username;
	
	private String firstName;
	private String lastName;
	
	private Map<String, Object> permissions;
	
	public User() {
	}
	
	public User(String firstName, String lastName, Map<String, Object> permissions) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.permissions = permissions;
	}
	
	@Override
	public String getKey() {
		return username;
	}
	
	@Override
	public void setKey(String key) {
		username = key;
	}
	
	@PropertyName("first_name")
	public String getFirstName() {
		return firstName;
	}
	
	@PropertyName("first_name")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@PropertyName("last_name")
	public String getLastName() {
		return lastName;
	}
	
	@PropertyName("last_name")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Map<String, Object> getPermissions() {
		return permissions;
	}
	
	public void setPermissions(Map<String, Object> permissions) {
		this.permissions = permissions;
	}
}
