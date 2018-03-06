package me.gablerlog.webapp.view.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class Card extends CustomComponent {

	private Panel panel;
	private VerticalLayout panelContent;

	private CssLayout header;
	private CssLayout body;
	private HorizontalLayout actions;

	public Card() {
		panel = new Panel();
		panel.setSizeFull();
		setCompositionRoot(panel);
		panelContent = new VerticalLayout();
		panelContent.setSizeFull();
		panel.setContent(panelContent);

		header = new CssLayout();
		header.setWidth(100, Unit.PERCENTAGE);
		body = new CssLayout();
		body.setWidth(100, Unit.PERCENTAGE);
		actions = new HorizontalLayout();
		actions.setWidth(100, Unit.PERCENTAGE);
		panelContent.addComponents(header, body, actions);
	}

	public void setHeaderContent(Component content) {
		header.removeAllComponents();
		header.addComponent(content);
	}

	public void setBodyContent(Component content) {
		body.removeAllComponents();
		body.addComponent(content);
	}

	public void addAction(Button action) {
		actions.addComponent(action);
	}

	public void removeAction(Button action) {
		actions.removeComponent(action);
	}
}
