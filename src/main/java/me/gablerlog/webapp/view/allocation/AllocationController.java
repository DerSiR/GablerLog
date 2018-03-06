package me.gablerlog.webapp.view.allocation;

import java.util.Set;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;

import me.gablerlog.webapp.db.Firebase;
import me.gablerlog.webapp.db.FirebaseDataProvider;
import me.gablerlog.webapp.db.RealtimeDatabase;
import me.gablerlog.webapp.db.entity.Category;
import me.gablerlog.webapp.db.entity.Order;
import me.gablerlog.webapp.db.entity.Transporter;

@SuppressWarnings("serial")
public class AllocationController implements View, AllocationView.Observer {
	
	private AllocationView view;
	
	private RealtimeDatabase				  db;
	private ListDataProvider<String>		  cargoDataProvider;
	private ListDataProvider<Category>		  providerCategory;
	private FirebaseDataProvider<Transporter> providerTransporter;
	private FirebaseDataProvider<Order>		  providerOrder;
	
	public AllocationController() {
		db = Firebase.get("gabler-log").getRealtimeDatabase();
		
		view = new AllocationView(this);
		
		Set<String> cargoData = db.getValues("cargo_types", Boolean.class).keySet();
		cargoDataProvider = new ListDataProvider<>(cargoData);
		view.setCargoTypeDataProvider(cargoDataProvider);
		
		providerCategory = DataProvider.ofCollection(Category.getCategories());
		view.setCategoryDataProvider(providerCategory);
		
		providerTransporter = new FirebaseDataProvider<>(
				Transporter.class, db.getReference("transporter"), cargoData);
		view.setTransporterDataProvider(providerTransporter);
		
		providerOrder = new FirebaseDataProvider<>(
				Order.class, db.getReference("vacant_orders"));
		view.setOrderDataProvider(providerOrder);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}
	
	@Override
	public void submit() {
		// TODO: Store data
	}
	
	@Override
	public Component getViewComponent() {
		return view;
	}
}
