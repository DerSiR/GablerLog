package me.gablerlog.webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.jsoup.nodes.Element;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.VaadinServlet;

public class Service {
	
	@SuppressWarnings("serial")
	@WebServlet(urlPatterns = "/*", name = "GablerLogUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = GablerLogUI.class, productionMode = false)
	public static class GablerLogUIServlet extends VaadinServlet {
		
		@Override
		protected void servletInitialized() throws ServletException {
			super.servletInitialized();
			getService().addSessionInitListener(sessionInitListener -> {
				sessionInitListener.getSession().addBootstrapListener(new BootstrapListener() {
					
					@Override
					public void modifyBootstrapPage(BootstrapPageResponse response) {
						final Element head = response.getDocument().head();
						
						head.appendElement("meta")
								.attr("name", "viewport")
								.attr("content", "width=device-width, initial-scale=1");
					}
					
					@Override
					public void modifyBootstrapFragment(BootstrapFragmentResponse response) {
					}
				});
			});
		}
	}
}
