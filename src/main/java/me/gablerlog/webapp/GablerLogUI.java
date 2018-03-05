package me.gablerlog.webapp;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import me.gablerlog.webapp.view.allocation.AllocationView;
import me.gablerlog.webapp.view.navigation.NavigationView;
import me.gablerlog.webapp.view.tracking.TrackingView;

/**
 * This UI is the application entry point. A UI may either
 * represent a browser window (or tab) or some part of an
 * HTML page where a Vaadin application is embedded. <p> The
 * UI is initialized using {@link #init(VaadinRequest)}.
 * This method is intended to be overridden to add component
 * to the user interface and initialize non-component
 * functionality.
 */
@SuppressWarnings("serial")
@Theme("gablerlogtheme")
public class GablerLogUI extends UI {
	
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final ApplicationDisplay display = new ApplicationDisplay();
		final Navigator navigator = new Navigator(this, display.getViewDisplay());
		setContent(display);
		
		navigator.addView(AllocationView.VIEW_NAME, AllocationView.class);
		navigator.addView(TrackingView.VIEW_NAME, TrackingView.class);
		navigator.addView(NavigationView.VIEW_NAME, NavigationView.class);
		
		String f = Page.getCurrent().getUriFragment();
		if (f == null || f.isEmpty()) {
			navigator.navigateTo(AllocationView.VIEW_NAME);
		}
	}
}
