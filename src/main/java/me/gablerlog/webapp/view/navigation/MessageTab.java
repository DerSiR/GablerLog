package me.gablerlog.webapp.view.navigation;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MessageTab extends VerticalLayout {
	
	public static final String TAB_NAME = "Messages";
	
	private Image imgCheck;
	
	private Label lblMessageState;
	
	public MessageTab() {
		
		imgCheck = new Image(null, new ThemeResource("res/img/navigate_check_transparent.png"));
		imgCheck.setWidth(50, Unit.PERCENTAGE);
		imgCheck.setHeight(50, Unit.PERCENTAGE);
		lblMessageState = new Label("No Messages");
		addComponents(imgCheck, lblMessageState);
	}
	
}

