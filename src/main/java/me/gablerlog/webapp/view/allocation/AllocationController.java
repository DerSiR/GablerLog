package me.gablerlog.webapp.view.allocation;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;

import me.gablerlog.webapp.db.Firebase;
import me.gablerlog.webapp.db.FirebaseDataProvider;
import me.gablerlog.webapp.db.RealtimeDatabase;
import me.gablerlog.webapp.db.entity.CargoType;
import me.gablerlog.webapp.db.entity.Category;
import me.gablerlog.webapp.db.entity.Transporter;

@SuppressWarnings("serial")
public class AllocationController implements View, AllocationView.Observer {
	
	private AllocationView view;
	
	private RealtimeDatabase				  db;
	private FirebaseDataProvider<CargoType>	  providerCargoType;
	private FirebaseDataProvider<Category>	  providerCategory;
	private FirebaseDataProvider<Transporter> providerTransporter;
	
	public AllocationController() {
		db = Firebase.get("gabler-log").getRealtimeDatabase();
		
		view = new AllocationView(this);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		providerCargoType = new FirebaseDataProvider<>(
				CargoType.class, db.getReference("cargo_types"));
		view.setCargoTypeDataProvider(providerCargoType.forLists());
		
		providerCategory = new FirebaseDataProvider<>(
				Category.class, db.getReference("categories"));
		view.setCategoryDataProvider(providerCategory);
		
		providerTransporter = new FirebaseDataProvider<>(
				Transporter.class, db.getReference("transporter"));
		view.setTransporterDataProvider(providerTransporter);
		
		View.super.enter(event);
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
