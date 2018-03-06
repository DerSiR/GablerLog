package me.gablerlog.webapp.view.navigation;

import com.vaadin.navigator.View;
import com.vaadin.ui.TabSheet;


@SuppressWarnings("serial")
public class NavigationView extends TabSheet implements View {
	
	public static final String VIEW_NAME = "navigation";
	
	private NavigationTab	tabNavigation;
	private OrderTab 		tabOrder;
	private MessageTab		tabMessage;
	
	public NavigationView() {
		
		setSizeFull();
		
		tabNavigation = new NavigationTab();
		addTab(tabNavigation, NavigationTab.TAB_NAME);
		
		tabOrder = new OrderTab();
		addTab(tabOrder, OrderTab.TAB_NAME);
		
		tabMessage = new MessageTab();
		addTab(tabMessage, MessageTab.TAB_NAME);
		
	}
}
