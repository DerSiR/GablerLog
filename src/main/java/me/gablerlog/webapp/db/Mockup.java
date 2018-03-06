package me.gablerlog.webapp.db;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.firebase.database.DatabaseReference;

import me.gablerlog.webapp.Util;
import me.gablerlog.webapp.db.entity.Capacity;
import me.gablerlog.webapp.db.entity.Location;
import me.gablerlog.webapp.db.entity.Order;
import me.gablerlog.webapp.db.entity.Transporter;
import me.gablerlog.webapp.db.entity.User;

public class Mockup {
	
	public static void generateMockupData() throws IOException {
		RealtimeDatabase db = Firebase.open("gabler-log").getRealtimeDatabase();
		
		Map<String, Object> cargoTypes = genCargoTypes();
		db.updateChildren("cargo_types", cargoTypes);
		
		Map<String, Object> transporterMappings = new HashMap<>();
		
		Map<String, Object> tps = new HashMap<>();
		
		DatabaseReference refTransporter = db.getReference("transporter");
		cargoTypes.forEach((e, f) -> {
			Map<String, Object> transporter = genTransporter();
			db.updateChildren(refTransporter.child(e), transporter);
			
			transporter.forEach((g, h) -> {
				transporterMappings.put(g, e);
			});
			
			Entry<String, Object> tp = transporter.entrySet().stream().findFirst().get();
			tps.put(tp.getKey(), tp.getValue());
		});
		
		db.updateChildren("transporter_mappings", transporterMappings);
		
		db.updateChildren("users", genUsers());
		
		db.updateChildren("vacant_orders", genOrders());
		
		Map<String, Object> trackingNumbers = new HashMap<>();
		
		DatabaseReference refRoutes = db.getReference("routes");
		tps.forEach((e, f) -> {
			Map<String, Object> orders = genOrders();
			db.updateChildren(refRoutes.child(e).child("orders"), orders);
			
			orders.forEach((g, h) -> {
				trackingNumbers.put(g, e);
			});
		});
		
		db.updateChildren("tracking_numbers", trackingNumbers);
	}
	
	private static Map<String, Object> genCargoTypes() {
		return Map.of("solid", true, "liquid", true, "gas", true);
	}
	
	private static Map<String, Object> genTransporter() {
		Map<String, Object> transporter = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			String uuid = UUID.randomUUID().toString();
			Transporter tp = new Transporter("", new Capacity(0, 0), new Location(0, 0, ""));
			transporter.put(uuid, tp);
		}
		return transporter;
	}
	
	private static Map<String, Object> genUsers() {
		User userDefault = new User("", "", Map.of("tracking", true));
		User userCustomer = new User("", "", Map.of("tracking", true));
		User userDriver = new User("", "", Map.of("tracking", true, "navigation", true));
		User userEditor = new User("", "", Map.of("tracking", true, "navigation", true, "allocation", true));
		User userAdmin = new User("", "", Map.of("tracking", true, "navigation", true, "allocation", true, "admin_interface", true));
		return Map.of("default", userDefault, "customer", userCustomer, "driver", userDriver, "editor", userEditor, "admin", userAdmin);
	}
	
	private static long orderId = 1;
	
	private static Map<String, Object> genOrders() {
		Map<String, Object> orders = new HashMap<>();
		for (long i = orderId + 10; orderId < i; orderId++) {
			String trackingNumber = Util.generateTrackingID(orderId);
			Order o = new Order(new Location(0, 0, ""), new Location(0, 0, ""), new Capacity(0, 0), 0, 0, "");
			orders.put(trackingNumber, o);
		}
		return orders;
	}
}
