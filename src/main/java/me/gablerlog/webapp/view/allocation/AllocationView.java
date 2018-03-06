package me.gablerlog.webapp.view.allocation;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet;

import me.gablerlog.webapp.db.FirebaseDataProvider;
import me.gablerlog.webapp.db.entity.CargoType;
import me.gablerlog.webapp.db.entity.Category;
import me.gablerlog.webapp.db.entity.Transporter;

@SuppressWarnings("serial")
public class AllocationView extends TabSheet implements View {
	
	public static final String VIEW_NAME = "allocation";
	
	private final Observer observer;
	
	private SelectCategoryTab	 tabCategory;
	private SelectTransporterTab tabTransporter;
	private SelectOrderTab		 tabOrder;
	
	private Button btnSubmit;
	
	public AllocationView(Observer observer) {
		this.observer = observer;
		
		setSizeFull();
		
		tabCategory = new SelectCategoryTab();
		addTab(tabCategory, SelectCategoryTab.TAB_NAME);
		
		tabTransporter = new SelectTransporterTab();
		addTab(tabTransporter, SelectTransporterTab.TAB_NAME);
		
		tabOrder = new SelectOrderTab();
		addTab(tabOrder, SelectOrderTab.TAB_NAME);
		
		btnSubmit = new Button();
		btnSubmit.setIcon(VaadinIcons.CHECK);
		btnSubmit.addStyleName("material-fab");
		addComponent(btnSubmit);
		
		handleDataEvents();
	}
	
	private void handleDataEvents() {
		btnSubmit.addClickListener(e -> {
			observer.submit();
		});
	}
	
	void setCargoTypeDataProvider(ListDataProvider<CargoType> dataProvider) {
		tabCategory.cbCargoType.setDataProvider(dataProvider);
	}
	
	void setCategoryDataProvider(FirebaseDataProvider<Category> dataProvider) {
		tabCategory.gdCategory.setDataProvider(dataProvider);
	}
	
	void setTransporterDataProvider(FirebaseDataProvider<Transporter> dataProvider) {
		tabTransporter.gdTransporter.setDataProvider(dataProvider);
		tabTransporter.gdTransporter.removeColumn("key");
	}
	
	interface Observer {
		void submit();
	}
}
