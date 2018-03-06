package me.gablerlog.webapp.view.navigation;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class NavigationTab extends VerticalLayout {
	
	public static final String TAB_NAME = "Navigation";
	
	private Label lblMap;
	
	public NavigationTab() {
		setSizeFull();
		
		lblMap = new Label();
		lblMap.setValue("<iframe frameborder=\"0\" src=\"https://www.google.com/maps/embed/v1/directions?key=AIzaSyAQ72OSGMPgN5Q6ktHtM2VJVFyUBFEUl7M&origin=49.4226858,8.3624595&destination=50.1932061,8.727795\" style=\"width: 100%; height: 100%; border: none;\"></iframe>");
		lblMap.setContentMode(ContentMode.HTML);
		lblMap.setSizeFull();
		
		addComponent(lblMap);
		
	}
	
}
