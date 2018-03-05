package me.gablerlog.webapp.view.allocation;

import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class SelectOrderTab extends VerticalLayout {
	public static final String TAB_NAME = "Order";
	private Grid<Object> gdOrder;

	public SelectOrderTab() {
		gdOrder = new Grid<>();

		gdOrder.setCaption("Order");
		gdOrder.setSizeFull();

		addComponent(gdOrder);
	}
}
