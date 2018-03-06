package me.gablerlog.webapp.db;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.DataProviderListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.Registration;

/**
 * A {@link DataProvider} for firebase databases.
 * 
 * @author Artur-
 * @see <a
 *      href="https://github.com/Artur-/vaadin-on-fire/blob/master/src/main/java/org/vaadin/artur/firebase/FirebaseDataProvider.java">https://github.com/Artur-/vaadin-on-fire/blob/master/src/main/java/org/vaadin/artur/firebase/FirebaseDataProvider.java</a>
 *
 * @param <T>
 */
@SuppressWarnings("serial")
public class FirebaseDataProvider<T extends HasKey>
		extends AbstractDataProvider<T, SerializablePredicate<T>>
		implements ChildEventListener {
	
	private LinkedHashMap<String, T> data				 = new LinkedHashMap<>();
	private final DatabaseReference	 rootReference;
	private FirebaseLocation[]		 locations;
	private Predicate<T>			 localFilter;
	private Class<T>				 type;
	private AtomicInteger			 registeredListeners = new AtomicInteger(0);
	
	private static class FirebaseLocation {
		
		private final DatabaseReference			   databaseReference;
		private com.google.firebase.database.Query filteredReference;
		
		public FirebaseLocation(DatabaseReference reference) {
			databaseReference = reference;
			filteredReference = reference;
		}
	}
	
	/**
	 * Constructs a new Firebase data provider connected to the
	 * given database reference.
	 *
	 * @param type
	 *            the entity type to use for items
	 * @param databaseReference
	 *            the reference containing the child nodes to
	 *            include
	 */
	public FirebaseDataProvider(Class<T> type, DatabaseReference databaseReference) {
		rootReference = databaseReference;
		locations = new FirebaseLocation[] { new FirebaseLocation(databaseReference) };
		this.type = type;
	}
	
	public FirebaseDataProvider(Class<T> type, DatabaseReference databaseReference, Collection<String> children) {
		rootReference = databaseReference;
		locations = new FirebaseLocation[children.size()];
		int index = 0;
		for (String child : children) {
			locations[index] = new FirebaseLocation(databaseReference.child(child));
			index++;
		}
		this.type = type;
	}
	
	@Override
	public String getId(T item) {
		return item.getKey();
	}
	
	@Override
	public Stream<T> fetch(Query<T, SerializablePredicate<T>> query) {
		if (localFilter != null) {
			return data.values().stream().filter(localFilter);
		} else {
			return data.values().stream();
		}
	}
	
	@Override
	public int size(Query<T, SerializablePredicate<T>> query) {
		if (localFilter != null) {
			return (int) data.values().stream()
					.filter(localFilter)
					.count();
		} else {
			return data.size();
		}
	}
	
	@Override
	public boolean isInMemory() {
		return false;
	}
	
	@Override
	public Registration addDataProviderListener(DataProviderListener<T> listener) {
		if (registeredListeners.incrementAndGet() == 1) {
			registerFirebaseListener();
		}
		Registration realRegistration = super.addDataProviderListener(listener);
		
		return () -> {
			realRegistration.remove();
			if (registeredListeners.decrementAndGet() == 0) {
				unregisterFirebaseListener();
			}
		};
	}
	
	private void registerFirebaseListener() {
		for (FirebaseLocation loc : locations) {
			loc.filteredReference.addChildEventListener(this);
		}
	}
	
	private void unregisterFirebaseListener() {
		for (FirebaseLocation loc : locations) {
			loc.filteredReference.removeEventListener(this);
		}
	}
	
	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		T added = snapshot.getValue(type);
		String key = snapshot.getKey();
		added.setKey(key);
		
		data.put(key, added);
		refreshAll();
	}
	
	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		T updated = snapshot.getValue(type);
		String key = snapshot.getKey();
		updated.setKey(key);
		
		data.put(key, updated);
		refreshItem(updated);
	}
	
	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		data.remove(snapshot.getKey());
		refreshAll();
	}
	
	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
	}
	
	@Override
	public void onCancelled(DatabaseError error) {
	}
	
	public void filter(Function<DatabaseReference, com.google.firebase.database.Query> filter) {
		removeFilter();
		for (FirebaseLocation loc : locations) {
			loc.filteredReference = filter.apply(loc.databaseReference);
		}
		refreshAll();
	}
	
	public void removeFilter() {
		unregisterFirebaseListener();
		for (FirebaseLocation loc : locations) {
			loc.filteredReference = loc.databaseReference;
		}
		refreshAll();
	}
	
	public void filterLocal(Predicate<T> filter) {
		localFilter = filter;
		refreshAll();
	}
	
	public void removeLocalFilter() {
		localFilter = null;
		refreshAll();
	}
	
	public DatabaseReference getReference() {
		return rootReference;
	}
	
	public FirebaseListDataProvider<T> forLists() {
		return new FirebaseListDataProvider<>(this);
	}
	
	public static class FirebaseListDataProvider<T extends HasKey>
			extends ListDataProvider<T>
			implements ChildEventListener {
		
		private FirebaseDataProvider<T> dataProvider;
		
		public FirebaseListDataProvider(FirebaseDataProvider<T> dataProvider) {
			super(dataProvider.data.values());
			
			this.dataProvider = dataProvider;
		}
		
		@Override
		public String getId(T item) {
			return dataProvider.getId(item);
		}
		
		@Override
		public Stream<T> fetch(Query<T, SerializablePredicate<T>> query) {
			return dataProvider.data.values().stream();
		}
		
		@Override
		public int size(Query<T, SerializablePredicate<T>> query) {
			return dataProvider.data.size();
		}
		
		@Override
		public boolean isInMemory() {
			return dataProvider.isInMemory();
		}
		
		@Override
		public Registration addDataProviderListener(DataProviderListener<T> listener) {
			return dataProvider.addDataProviderListener(listener);
		}
		
		@Override
		public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
			dataProvider.onChildAdded(snapshot, previousChildName);
			refreshAll();
		}
		
		@Override
		public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
			dataProvider.onChildChanged(snapshot, previousChildName);
			refreshItem(snapshot.getValue(dataProvider.type));
		}
		
		@Override
		public void onChildRemoved(DataSnapshot snapshot) {
			dataProvider.onChildRemoved(snapshot);
			refreshAll();
		}
		
		@Override
		public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		}
		
		@Override
		public void onCancelled(DatabaseError error) {
		}
		
		public void filter(Function<DatabaseReference, com.google.firebase.database.Query> filter) {
			dataProvider.filter(filter);
			refreshAll();
		}
		
		public void removeFilter() {
			dataProvider.removeFilter();
			refreshAll();
		}
		
		public void filterLocal(Predicate<T> filter) {
			dataProvider.filterLocal(filter);
			refreshAll();
		}
		
		public void removeLocalFilter() {
			dataProvider.removeLocalFilter();
			refreshAll();
		}
		
		public DatabaseReference getReference() {
			return dataProvider.getReference();
		}
	}
}
