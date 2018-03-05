package me.gablerlog.webapp.view.allocation;

import com.vaadin.navigator.View;
import com.vaadin.ui.TabSheet;

@SuppressWarnings("serial")
public class AllocationView extends TabSheet implements View {
	public static final String VIEW_NAME = "allocation";
	private SelectCategoryTab tabCategory;
	private SelectTransporterTab tabTransporter;
	private SelectOrderTab tabOrder;

	public AllocationView() {

		tabCategory = new SelectCategoryTab();
		tabTransporter = new SelectTransporterTab();
		tabOrder = new SelectOrderTab();

		addTab(tabCategory, SelectCategoryTab.TAB_NAME);
		addTab(tabTransporter, SelectTransporterTab.TAB_NAME);
		addTab(tabOrder, SelectOrderTab.TAB_NAME);

		setSizeFull();
	}
}
