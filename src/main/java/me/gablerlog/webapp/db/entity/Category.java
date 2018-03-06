package me.gablerlog.webapp.db.entity;

import java.util.Arrays;
import java.util.List;

public class Category {
	
	public static final Category AT_HEADQUARTER	  = new Category("At Headquarter");
	public static final Category EMPTY_TRIP		  = new Category("Empty Trip");
	public static final Category SINGLE_TRIP_LEFT = new Category("1 Trip left in Route");
	public static final Category HIGH_CAPACITY	  = new Category("High Capacity");
	
	private String title;
	
	private Category(String title) {
		this.title = title;
	}
	
	public static List<Category> getCategories() {
		return Arrays.asList(
				AT_HEADQUARTER,
				EMPTY_TRIP,
				SINGLE_TRIP_LEFT,
				HIGH_CAPACITY);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
