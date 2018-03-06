package me.gablerlog.webapp.view.navigation;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import me.gablerlog.webapp.component.Card;

@SuppressWarnings("serial")
public class OrderTab extends VerticalLayout {
	
	public static final String TAB_NAME = "Orders";
	
	private static final Image imgTransmitted = 
			new Image(null,	new ThemeResource("res/img/gl_delivery_note_secondary.png"));
	private static final Image imgPending = 
			new Image(null, new ThemeResource("res/img/gl_box_into_secondary.png"));
	private static final Image imgOnRoad = 
			new Image(null, new ThemeResource("res/img/gl_truck2_secondary.png"));
	private static final Image imgDelivered = 
			new Image(null, new ThemeResource("res/img/gl_box_out_secondary.png"));
	
	private Card cOrder;
	
	private Label lblTrackingNumber;
	private Label lblLocation;
	private Label lblOrderInfo;
	private Label lblStartLocation;
	private Label lblEndLocation;
	
	private Button btnLoaded;
	private Button btnDelivered;
	
	private VerticalLayout vHeaderLayout;
	private HorizontalLayout hHeaderLayout;
	
	private VerticalLayout vBodyLayout;
	
	public OrderTab() {
		
		lblTrackingNumber = new Label("GL-XXX");
		lblLocation = new Label("Mannheim - Frankfurt");
		
		
		vHeaderLayout = new VerticalLayout();
		vHeaderLayout.setMargin(false);
		vHeaderLayout.setSpacing(false);
		vHeaderLayout.addComponents(lblTrackingNumber,lblLocation);
		
		hHeaderLayout = new HorizontalLayout();
		hHeaderLayout.addComponents(resizeImage(imgOnRoad), vHeaderLayout);
		
		
		lblOrderInfo = new Label("18 mt - Building Materials");
		lblStartLocation = new Label("S - Hermes Paketshop - Mannheim");
		lblEndLocation = new Label("E - Bauzentrum Maeusel - Frankfurt");
		
		vBodyLayout = new VerticalLayout();
		vBodyLayout.addComponents(lblOrderInfo, lblStartLocation, lblEndLocation);
		
		
		btnLoaded = new Button("Loaded");
		btnDelivered = new Button("Delivered");
		

		cOrder = new Card();
		cOrder.setHeaderContent(hHeaderLayout);
		cOrder.setBodyContent(vBodyLayout);
		cOrder.addAction(btnLoaded);
		cOrder.addAction(btnDelivered);
		addComponent(cOrder);
		
	}
	
	private Image resizeImage(Image img) {
		img.setHeight(64, Unit.PIXELS);
		img.setWidth(64, Unit.PIXELS);
		return img;
	}
}
