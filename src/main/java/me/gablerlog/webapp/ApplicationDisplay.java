package me.gablerlog.webapp;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import me.gablerlog.webapp.view.allocation.AllocationView;
import me.gablerlog.webapp.view.navigation.NavigationView;
import me.gablerlog.webapp.view.tracking.TrackingView;

@SuppressWarnings("serial")
public class ApplicationDisplay extends HorizontalLayout {
	
	private CssLayout menu;
	private Label	  lblTitle;
	
	private CssLayout menuPart;
	private Button	  btnAllocation, btnTracking, btnNavigation;
	
	private CssLayout viewContainer;
	
	public ApplicationDisplay() {
		addStyleName(ValoTheme.UI_WITH_MENU);
		setSizeFull();
		setSpacing(false);
//		setResponsive(true);
		
		menu = new CssLayout();
		menu.setPrimaryStyleName(ValoTheme.MENU_ROOT);
		addComponent(menu);
		
		lblTitle = new Label("Menu");
		lblTitle.addStyleName(ValoTheme.MENU_TITLE);
		menu.addComponent(lblTitle);
		
		menuPart = new CssLayout();
		menuPart.addStyleName(ValoTheme.MENU_PART);
		menu.addComponent(menuPart);
		
		btnAllocation = new Button("Allocation",
				e -> UI.getCurrent().getNavigator().navigateTo(AllocationView.VIEW_NAME));
		btnAllocation.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		btnAllocation.setIcon(VaadinIcons.INBOX);
		menuPart.addComponent(btnAllocation);
		
		btnTracking = new Button("Tracking",
				e -> UI.getCurrent().getNavigator().navigateTo(TrackingView.VIEW_NAME));
		btnTracking.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		btnTracking.setIcon(VaadinIcons.MAP_MARKER);
		menuPart.addComponent(btnTracking);
		
		btnNavigation = new Button("Navigation",
				e -> UI.getCurrent().getNavigator().navigateTo(NavigationView.VIEW_NAME));
		btnNavigation.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		btnNavigation.setIcon(VaadinIcons.LOCATION_ARROW_CIRCLE);
		menuPart.addComponent(btnNavigation);
		
		viewContainer = new CssLayout();
		viewContainer.setSizeFull();
		addComponent(viewContainer);
		setExpandRatio(viewContainer, 1);
	}
	
	public ComponentContainer getViewDisplay() {
		return viewContainer;
	}
}
