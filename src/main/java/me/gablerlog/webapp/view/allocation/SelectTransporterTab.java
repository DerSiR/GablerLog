package me.gablerlog.webapp.view.allocation;

import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import me.gablerlog.webapp.db.entity.Transporter;

@SuppressWarnings("serial")
public class SelectTransporterTab extends VerticalLayout {
	
	public static final String TAB_NAME = "Transporter";
	
	Grid<Transporter> gdTransporter;
	
	public SelectTransporterTab() {
		gdTransporter = new Grid<>();
		gdTransporter.setCaption("Transporter");
		gdTransporter.setSizeFull();
		
		addComponent(gdTransporter);
	}
}
