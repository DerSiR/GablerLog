package me.gablerlog.webapp.view.allocation;

import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;

import me.gablerlog.webapp.db.entity.Order;

@SuppressWarnings("serial")
public class SelectOrderTab extends VerticalLayout {
	
	public static final String TAB_NAME = "Order";
	
	Grid<Order> gdOrder;
	
	public SelectOrderTab() {
		gdOrder = new Grid<>(Order.class);
		gdOrder.setCaption("Order");
		gdOrder.setSizeFull();
		gdOrder.setSelectionMode(SelectionMode.MULTI);
		addComponent(gdOrder);
	}
}
