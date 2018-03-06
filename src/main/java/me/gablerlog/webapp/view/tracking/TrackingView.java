package me.gablerlog.webapp.view.tracking;

import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import me.gablerlog.webapp.component.Card;

@SuppressWarnings("serial")
public class TrackingView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "tracking";
	
	private Card cTrackingNumber;
	private Card cMap;
	
	private TextField tfTrackingNumber;
	private Button	  btnTrack;
	
	private VerticalLayout	 vLayout;
	private HorizontalLayout hLayout;
	private Label			 lblTrackingNumber;
	private Label			 lblLocation;
	
	private Image imgMap;
	
	private static final Image imgTransmitted = new Image(null, new ThemeResource("res/img/gl_delivery_note_secondary.png"));
	private static final Image imgPending	  = new Image(null, new ThemeResource("res/img/gl_box_into_secondary.png"));
	private static final Image imgOnRoad	  = new Image(null, new ThemeResource("res/img/gl_truck2_secondary.png"));
	private static final Image imgDelivered	  = new Image(null, new ThemeResource("res/img/gl_box_out_secondary.png"));
	
	public TrackingView() {
		
		tfTrackingNumber = new TextField();
		tfTrackingNumber.setPlaceholder("Tracking Number");
		
		btnTrack = new Button("Track");
		
		cTrackingNumber = new Card();
		cTrackingNumber.setHeaderContent(tfTrackingNumber);
		cTrackingNumber.addAction(btnTrack);
		addComponent(cTrackingNumber);
		
		lblTrackingNumber = new Label("GL-XXX");
		lblLocation = new Label("Mannheim - Nürnberg");
		imgMap = new Image();
		imgMap.setSource(new ExternalResource("https://maps.googleapis.com/maps/api/staticmap?size=344x192&path=color:0x0000ff%7Cweight:5%7C49.4226858,8.3624595%7C50.1932061,8.727795&markers=color:blue%7Clabel:S%7C49.4226858,8.3624595&markers=color:blue%7Clabel:E%7C50.1932061,8.727795&key=AIzaSyAQ72OSGMPgN5Q6ktHtM2VJVFyUBFEUl7M"));
		
		vLayout = new VerticalLayout();
		vLayout.setMargin(false);
		vLayout.setSpacing(false);
		vLayout.addComponents(lblTrackingNumber, lblLocation);
		
		hLayout = new HorizontalLayout();
		hLayout.addComponents(resizeImage(imgOnRoad), vLayout);
		
		cMap = new Card();
		cMap.setHeaderContent(hLayout);
		cMap.setBodyContent(imgMap);
		addComponent(cMap);
		
		btnTrack.addClickListener(e -> {
			lblTrackingNumber.setValue(tfTrackingNumber.getValue());
		});
	}
	
	private Image resizeImage(Image img) {
		img.setHeight(64, Unit.PIXELS);
		img.setWidth(64, Unit.PIXELS);
		return img;
		
	}
	
}
