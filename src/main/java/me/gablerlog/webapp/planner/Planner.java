package me.gablerlog.webapp.planner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;

import me.gablerlog.webapp.Util;
import me.gablerlog.webapp.db.entity.Order;
import me.gablerlog.webapp.db.entity.Route;
import me.gablerlog.webapp.db.entity.Transporter;

public class Planner {
	
	private Transporter	transporter;
	private Route		route;
	private boolean		adjustRoute;
	
	public Planner(Transporter transporter, boolean adjustRoute) {
		this.transporter = transporter;
		this.adjustRoute = adjustRoute;
	}
	
	public Planner(Transporter transporter, boolean adjustRoute, Route route) {
		this(transporter, adjustRoute);
		this.route = route;
	}
	
	public Route calculateRoute(Set<Order> orders) {
		int countOrders = orders.size() + 1;
		Order oFirst;
		if (route != null) {
			if (adjustRoute) {
				oFirst = route.getOrders().getFirst();
				countOrders += route.getOrders().size() - 1;
			} else {
				oFirst = route.getOrders().getLast();
			}
		} else {
			oFirst = new Order();
			oFirst.setDestination(transporter.getLocation());
		}
		
		int newIndex = 0;
		Order[] oArr = new Order[countOrders];
		oArr[newIndex++] = oFirst;
		if (route != null && adjustRoute) {
			for (Order o : route.getOrders()) {
				oArr[newIndex++] = o;
			}
		}
		
		int index = 0;
		for (Order o : orders) {
			oArr[newIndex + index++] = o;
		}
		
		final Dist[][] DISTANCE_MATRIX = new Dist[oArr.length][oArr.length - 2];
		for (int i = 0; i < oArr.length; i++) {
			Order os = oArr[i];
			for (int j = 0; j < oArr.length; j++) {
				Order oe = oArr[j];
				if (oe == oFirst) break;
				if (oe == os) continue;
				Dist dist = new Dist();
				dist.distance = Util.calcAirDistance(os.getDestination(), oe.getOrigin());
				dist.order = oe;
				DISTANCE_MATRIX[i][j] = dist;
			}
		}
		
		LinkedList<Order> r = new LinkedList<>();
		Order o = oArr[0];
		for (int i = 0; i < oArr.length; i++) {
			r.add(o);
			Dist[] current = null;
			for (int j = 0; j < oArr.length; j++) {
				if (oArr[j] == o) {
					current = DISTANCE_MATRIX[j];
					break;
				}
			}
			Arrays.sort(current);
			r.add(o = current[0].order);
		}
		
		// TODO: Implement algorithm further for comparing distances
		// between individual orders
		
		return new Route(r);
	}
	
	private static class Dist implements Comparable<Dist> {
		private Order  order;
		private double distance;
		
		@Override
		public int compareTo(Dist dist) {
			if (distance == dist.distance) return 0;
			if (distance < dist.distance) return -1;
			return 1;
		}
	}
}
