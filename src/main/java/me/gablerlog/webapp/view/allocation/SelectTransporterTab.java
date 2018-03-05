package me.gablerlog.webapp.view.allocation;

import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class SelectTransporterTab extends VerticalLayout {
	public static final String TAB_NAME = "Transporter";
	private Grid<Object> gdTransporter;

	public SelectTransporterTab() {
		gdTransporter = new Grid<>();

		gdTransporter.setCaption("Transporter");
		gdTransporter.setSizeFull();

		addComponent(gdTransporter);
	}
}
