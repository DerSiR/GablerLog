package me.gablerlog.webapp.view.allocation;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import me.gablerlog.webapp.Util;
import me.gablerlog.webapp.db.entity.CargoType;
import me.gablerlog.webapp.db.entity.Category;

@SuppressWarnings("serial")
public class SelectCategoryTab extends VerticalLayout {
	
	public static final String TAB_NAME = "Category";
	
	ComboBox<CargoType>	cbCargoType;
	Grid<Category>		gdCategory;
	
	public SelectCategoryTab() {
		cbCargoType = new ComboBox<>("Cargo Type");
		cbCargoType.setItemCaptionGenerator(
				type -> Util.capitalizeFirstLetter(type.getKey()));
		addComponent(cbCargoType);
		
		gdCategory = new Grid<>();
		gdCategory.setCaption("Category");
		addComponent(gdCategory);
	}
}
