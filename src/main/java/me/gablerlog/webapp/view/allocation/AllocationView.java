package me.gablerlog.webapp.view.allocation;

import java.util.Set;

import com.google.firebase.database.DatabaseReference;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;

import me.gablerlog.webapp.Util;
import me.gablerlog.webapp.db.Firebase;
import me.gablerlog.webapp.db.FirebaseDataProvider;
import me.gablerlog.webapp.db.RealtimeDatabase;
import me.gablerlog.webapp.db.entity.Category;
import me.gablerlog.webapp.db.entity.Order;
import me.gablerlog.webapp.db.entity.Transporter;

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
		
		handleDataEvents();
	}
	
	private void handleDataEvents() {
		btnSubmit.addClickListener(e -> {
			observer.submit();
		});
		
		tabCategory.cbCargoType.addSelectionListener(e -> {
			if (!e.isUserOriginated()) return;
			
			ListDataProvider<String> cargoDataProvider = (ListDataProvider<String>) tabCategory.cbCargoType.getDataProvider();
			FirebaseDataProvider<Transporter> transporterDataProvider = (FirebaseDataProvider<Transporter>) tabTransporter.gdTransporter.getDataProvider();
			FirebaseDataProvider<Order> orderDataProvider = (FirebaseDataProvider<Order>) tabOrder.gdOrder.getDataProvider();
			
			DatabaseReference refTransporter = db.getReference("transporter");
			FirebaseDataProvider<Transporter> newDataProvider;
			
			if (e.getSelectedItem().isPresent()) {
				String cargo = e.getSelectedItem().get();
				refTransporter = refTransporter.child(cargo);
				newDataProvider = new FirebaseDataProvider<>(Transporter.class, refTransporter);
				
			} else {
				Set<String> cargoTypes = db.getValues("cargo_types", Boolean.class).keySet();
				newDataProvider = new FirebaseDataProvider<>(Transporter.class, refTransporter, cargoTypes);
			}
			
			tabTransporter.gdTransporter.setDataProvider(newDataProvider);
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
