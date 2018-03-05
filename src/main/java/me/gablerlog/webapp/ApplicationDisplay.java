package me.gablerlog.webapp;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import me.gablerlog.webapp.view.allocation.AllocationView;
import me.gablerlog.webapp.view.navigation.NavigationView;
import me.gablerlog.webapp.view.tracking.TrackingView;

@SuppressWarnings("serial")
public class ApplicationDisplay extends HorizontalLayout {

	private Label title;
	private Button view1, view2, view3;
	private CssLayout menu, menuPart;

	private VerticalLayout viewContainer;

	public ApplicationDisplay() {

		addStyleName(ValoTheme.UI_WITH_MENU);
		addStyleName("UI");
		setSizeFull();
		setSpacing(true);
		setResponsive(true);

		view1 = new Button("Allocation", e -> UI.getCurrent().getNavigator().navigateTo(AllocationView.VIEW_NAME));
		view1.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		view1.setIcon(VaadinIcons.INBOX);
		view2 = new Button("Tracking", e -> UI.getCurrent().getNavigator().navigateTo(TrackingView.VIEW_NAME));
		view2.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		view2.setIcon(VaadinIcons.MAP_MARKER);
		view3 = new Button("Navigation", e -> UI.getCurrent().getNavigator().navigateTo(NavigationView.VIEW_NAME));
		view3.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		view3.setIcon(VaadinIcons.LOCATION_ARROW_CIRCLE);

		viewContainer = new VerticalLayout();
		viewContainer.setSizeFull();
		viewContainer.setMargin(false);

		title = new Label("Menu");
		title.addStyleName(ValoTheme.MENU_TITLE);

		menuPart = new CssLayout();
		menuPart.addStyleName(ValoTheme.MENU_PART);
		menuPart.addComponents(title, view1, view2, view3);

		menu = new CssLayout();
		menu.setPrimaryStyleName(ValoTheme.MENU_ROOT);

		menu.addComponents(menuPart);

		addComponent(menu);
		addComponent(viewContainer);
		setExpandRatio(menu, 0);
		setExpandRatio(viewContainer, 1);

	}

	public ComponentContainer getViewDisplay() {
		return viewContainer;
	}

}
