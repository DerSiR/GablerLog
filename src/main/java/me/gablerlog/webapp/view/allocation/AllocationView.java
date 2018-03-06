package me.gablerlog.webapp.view.allocation;

import java.util.Set;

import com.google.firebase.database.DatabaseReference;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import me.gablerlog.webapp.Util;
import me.gablerlog.webapp.component.Card;
import me.gablerlog.webapp.db.Firebase;
import me.gablerlog.webapp.db.FirebaseDataProvider;
import me.gablerlog.webapp.db.RealtimeDatabase;
import me.gablerlog.webapp.db.entity.Category;
import me.gablerlog.webapp.db.entity.Order;
import me.gablerlog.webapp.db.entity.Route;
import me.gablerlog.webapp.db.entity.Transporter;
import me.gablerlog.webapp.planner.RoutePlanner;

@SuppressWarnings("serial")
public class AllocationView extends CssLayout implements View {
	
	public static final String VIEW_NAME = "allocation";
	
	private final Observer observer;
	
	private RealtimeDatabase db;
	
	private TabSheet contentLayout;
	
	private SelectCategoryTab	 tabCategory;
	private SelectTransporterTab tabTransporter;
	private SelectOrderTab		 tabOrder;
	
	private Button btnSubmit;
	
	private Window wdParameters;
	private Card   cdParameters;
	
	private VerticalLayout parametersLayout;
	private CheckBox	   ckbAdjustRoute;
	private CheckBox	   ckbDistributeOrders;
	
	private Button btnCancel;
	private Button btnPlanRoute;
	
	public AllocationView(Observer observer) {
		db = Firebase.get("gabler-log").getRealtimeDatabase();
		
		this.observer = observer;
		
		setSizeFull();
		
		contentLayout = new TabSheet();
		contentLayout.setSizeFull();
		addComponent(contentLayout);
		
		tabCategory = new SelectCategoryTab();
		contentLayout.addTab(tabCategory, SelectCategoryTab.TAB_NAME);
		
		tabTransporter = new SelectTransporterTab();
		contentLayout.addTab(tabTransporter, SelectTransporterTab.TAB_NAME);
		
		tabOrder = new SelectOrderTab();
		contentLayout.addTab(tabOrder, SelectOrderTab.TAB_NAME);
		
		btnSubmit = new Button();
		btnSubmit.setIcon(VaadinIcons.CHECK);
		btnSubmit.addStyleName("material-fab");
		btnSubmit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		addComponent(btnSubmit);
		
		wdParameters = new Window("Parameters");
		cdParameters = new Card();
		wdParameters.setContent(cdParameters);
		
		parametersLayout = new VerticalLayout();
		cdParameters.setBodyContent(parametersLayout);
		
		ckbAdjustRoute = new CheckBox("Adjust route if necessary");
		ckbDistributeOrders = new CheckBox("Allow distributing orders");
		parametersLayout.addComponents(ckbAdjustRoute, ckbDistributeOrders);
		
		btnCancel = new Button("Cancel");
		cdParameters.addAction(btnCancel);
		
		btnPlanRoute = new Button("Plan Route");
		btnPlanRoute.setIcon(VaadinIcons.PAPERPLANE);
		btnPlanRoute.addStyleName(ValoTheme.BUTTON_PRIMARY);
		cdParameters.addAction(btnPlanRoute);
		
		handleDataEvents();
	}
	
	private void handleDataEvents() {
		tabCategory.cbCargoType.addSelectionListener(e -> {
			if (!e.isUserOriginated()) return;
			
			DatabaseReference refTransporter = db.getReference("transporter");
			FirebaseDataProvider<Transporter> transporterDataProvider;
			
			DatabaseReference refVacantOrders = db.getReference("vacant_orders");
			FirebaseDataProvider<Order> vacantOrdersDataProvider;
			
			if (e.getSelectedItem().isPresent()) {
				String cargo = e.getSelectedItem().get();
				refTransporter = refTransporter.child(cargo);
				transporterDataProvider = new FirebaseDataProvider<>(Transporter.class, refTransporter);
				
				refVacantOrders = refVacantOrders.child(cargo);
				vacantOrdersDataProvider = new FirebaseDataProvider<>(Order.class, refTransporter);
				
			} else {
				Set<String> cargoTypes = db.getValues("cargo_types", Boolean.class).keySet();
				transporterDataProvider = new FirebaseDataProvider<>(Transporter.class, refTransporter, cargoTypes);
				
				vacantOrdersDataProvider = new FirebaseDataProvider<>(Order.class, refTransporter, cargoTypes);
			}
			
			tabTransporter.gdTransporter.setDataProvider(transporterDataProvider);
			tabOrder.gdOrder.setDataProvider(vacantOrdersDataProvider);
		});
		
		btnSubmit.addClickListener(e -> {
			wdParameters.setModal(true);
			UI.getCurrent().addWindow(wdParameters);
			wdParameters.center();
		});
		
		btnCancel.addClickListener(e -> wdParameters.close());
		
		btnPlanRoute.addClickListener(e -> {
			String cargoType = tabCategory.cbCargoType.getSelectedItem().orElse(null);
			Category category = tabCategory.gdCategory.getSelectedItems().stream().findFirst().orElse(null);
			Transporter transporter = tabTransporter.gdTransporter.getSelectedItems().stream().findFirst().orElse(null);
			Set<Order> orders = tabOrder.gdOrder.getSelectedItems();
			
			RoutePlanner planner = new RoutePlanner(cargoType, category, transporter, orders);
			planner.setAdjustRoute(ckbAdjustRoute.getValue());
			planner.setDistributeOrders(ckbDistributeOrders.getValue());
			
			Route route = planner.planRoute();
			route.setKey(transporter.getKey());
			db.setValue("routes", route);
			
			Notification.show("Route planned", Type.TRAY_NOTIFICATION);
		});
	}
	
	void setCargoTypeDataProvider(ListDataProvider<String> dataProvider) {
		tabCategory.cbCargoType.setDataProvider(dataProvider);
	}
	
	void setCategoryDataProvider(ListDataProvider<Category> dataProvider) {
		tabCategory.gdCategory.setDataProvider(dataProvider);
	}
	
	void setTransporterDataProvider(FirebaseDataProvider<Transporter> dataProvider) {
		tabTransporter.gdTransporter.setDataProvider(dataProvider);
		tabTransporter.gdTransporter.setColumns("numberplate");
		tabTransporter.gdTransporter
				.addColumn(e -> e.getLocation().getName())
				.setCaption("Location");
		tabTransporter.gdTransporter
				.addColumn(e -> e.getCapacity().getWeight() + " kg")
				.setCaption("Capacity");
	}
	
	void setOrderDataProvider(FirebaseDataProvider<Order> dataProvider) {
		tabOrder.gdOrder.setDataProvider(dataProvider);
		tabOrder.gdOrder.setColumns("key");
		tabOrder.gdOrder
				.addColumn(e -> e.getOrigin().getName())
				.setCaption("Origin");
		tabOrder.gdOrder
				.addColumn(e -> e.getDestination().getName())
				.setCaption("Destination");
		tabOrder.gdOrder
				.addColumn(e -> e.getBulk().getWeight() + " kg")
				.setCaption("Bulk");
		Column<Order, String> colEta = tabOrder.gdOrder
				.addColumn(e -> Util.GENERAL_DATETIME_FORMATTER.format(e.getLatestDelivery()));
		colEta.setCaption("ETA");
		tabOrder.gdOrder.sort(colEta);
	}
	
	interface Observer {
		void submit();
	}
}
