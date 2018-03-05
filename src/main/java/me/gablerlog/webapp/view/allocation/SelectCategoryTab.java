package me.gablerlog.webapp.view.allocation;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class SelectCategoryTab extends VerticalLayout {

	public static final String TAB_NAME = "Category";

	private Grid<Object> gdPhysicType;
	private ComboBox<Object> cbPhysicCategory;
	private List<Object> logicCategory, physicCategory;

	public SelectCategoryTab() {

		gdPhysicType = new Grid<>();

		logicCategory = new ArrayList<>();

		physicCategory = new ArrayList<>();
		physicCategory.add("Pallets");
		physicCategory.add("Fluids");
		physicCategory.add("Gas");

		cbPhysicCategory = new ComboBox<>("Primary Category");
		cbPhysicCategory.setItems(physicCategory.toArray());

		gdPhysicType.setCaption("Secondary Category");

		addComponent(cbPhysicCategory);
		addComponent(gdPhysicType);

	}
}
