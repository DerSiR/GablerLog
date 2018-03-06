package me.gablerlog.webapp.planner;

import java.util.Map;
import java.util.Set;

import com.google.firebase.database.DatabaseReference;

import me.gablerlog.webapp.db.Firebase;
import me.gablerlog.webapp.db.RealtimeDatabase;
import me.gablerlog.webapp.db.entity.Category;
import me.gablerlog.webapp.db.entity.Order;
import me.gablerlog.webapp.db.entity.Route;
import me.gablerlog.webapp.db.entity.Transporter;

public class RoutePlanner {
	
	private String		cargoType;
	private Category	category;
	private Transporter	transporter;
	private Set<Order>	orders;
	
	private boolean	adjustRoute		 = false;
	private boolean	distributeOrders = false;
	
	public RoutePlanner() {
	}
	
	public RoutePlanner(String cargoType, Category category, Transporter transporter, Set<Order> orders) {
		this.cargoType = cargoType;
		this.category = category;
		this.transporter = transporter;
		this.orders = orders;
	}
	
	public void setAdjustRoute(boolean value) {
		adjustRoute = value;
	}
	
	public void setDistributeOrders(boolean value) {
		distributeOrders = value;
	}
	
	public Route planRoute() {
		if (orders == null || orders.isEmpty()) {
			throw new RuntimeException("Must select an order");
		}
		
		if (transporter == null) {
			if (cargoType == null || category == null) {
				throw new RuntimeException("Must select a category or transporter");
			}
			
			RealtimeDatabase db = Firebase.get("gabler-log").getRealtimeDatabase();
			DatabaseReference refTransporter = db.getReference("transporter");
			Map<String, Transporter> transporter = db.getValues(
					refTransporter.child(cargoType), Transporter.class);
			
			transporter.forEach((uuid, t) -> {
				Planner p = new Planner(t, adjustRoute, getCurrentRoute(t));
				p.calculateRoute(orders);
			});
			
			// TODO: Find shortest route
			
			return null;
			
		} else {
			Planner p = new Planner(transporter, adjustRoute, getCurrentRoute(transporter));
			return p.calculateRoute(orders);
		}
	}
	
	private Route getCurrentRoute(Transporter transporter) {
		RealtimeDatabase db = Firebase.get("gabler-log").getRealtimeDatabase();
		DatabaseReference refTransporter = db.getReference("routes");
		Route route = db.getValue(refTransporter.child(transporter.getKey()), Route.class);
		return route;
	}
}
