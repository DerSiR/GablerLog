package me.gablerlog.webapp;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

public class Service {
	
	@SuppressWarnings("serial")
	@WebServlet(urlPatterns = "/*", name = "GablerLogUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = GablerLogUI.class, productionMode = false)
	public static class GablerLogUIServlet extends VaadinServlet {
	}
}
