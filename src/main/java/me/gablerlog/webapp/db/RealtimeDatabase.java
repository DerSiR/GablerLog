package me.gablerlog.webapp.db;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RealtimeDatabase {
	
	public static final int DEFAULT_TIMEOUT = 60;
	
	private FirebaseDatabase database;
	
	public RealtimeDatabase(FirebaseDatabase database) {
		this.database = database;
	}
	
	public FirebaseDatabase getDatabase() {
		return database;
	}
	
	public DatabaseReference getReference(String ref) {
		return database.getReference(ref);
	}
	
	private static final Consumer<Throwable> DEFAULT_ERR_HANDLER = err -> {
		throw new RuntimeException(err);
	};
	
	private static final Consumer<DatabaseError> DEFAULT_DBERR_HANDLER = err -> {
		throw new RuntimeException(err.toException());
	};
	
	public <T> void setValue(String ref, T value) {
		_setValue(getReference(ref), value, null);
	}
	
	public <T> void setValue(String ref, T value, Number priority) {
		_setValue(getReference(ref), value, priority);
	}
	
	public <T> void setValue(String ref, T value, String priority) {
		_setValue(getReference(ref), value, priority);
	}
	
	public <T> void setValue(DatabaseReference ref, T value) {
		_setValue(ref, value, null);
	}
	
	public <T> void setValue(DatabaseReference ref, T value, Number priority) {
		_setValue(ref, value, priority);
	}
	
	public <T> void setValue(DatabaseReference ref, T value, String priority) {
		_setValue(ref, value, priority);
	}
	
	private <T> void _setValue(DatabaseReference ref, T value, Object priority) {
		try {
			Semaphore sem = new Semaphore(1);
			sem.acquire();
			
			_setValueAsync(ref, value, priority, () -> sem.release());
			
			sem.acquire();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public <T> void setValueAsync(String ref, T value, Runnable callback) {
		_setValueAsync(getReference(ref), value, null, callback);
	}
	
	public <T> void setValueAsync(String ref, T value, Number priority, Runnable callback) {
		_setValueAsync(getReference(ref), value, priority, callback);
	}
	
	public <T> void setValueAsync(String ref, T value, String priority, Runnable callback) {
		_setValueAsync(getReference(ref), value, priority, callback);
	}
	
	public <T> void setValueAsync(DatabaseReference ref, T value, Runnable callback) {
		_setValueAsync(ref, value, null, callback);
	}
	
	public <T> void setValueAsync(DatabaseReference ref, T value, Number priority, Runnable callback) {
		_setValueAsync(ref, value, priority, callback);
	}
	
	public <T> void setValueAsync(DatabaseReference ref, T value, String priority, Runnable callback) {
		_setValueAsync(ref, value, priority, callback);
	}
	
	private <T> void _setValueAsync(DatabaseReference ref, T value, Object priority, Runnable callback) {
		_setValueAsync(ref, value, priority, callback, DEFAULT_ERR_HANDLER);
	}
	
	public <T> void setValueAsync(String ref, T value, Runnable callback, Consumer<Throwable> errorHandler) {
		_setValueAsync(getReference(ref), value, null, callback, errorHandler);
	}
	
	public <T> void setValueAsync(String ref, T value, Number priority, Runnable callback, Consumer<Throwable> errorHandler) {
		_setValueAsync(getReference(ref), value, priority, callback, errorHandler);
	}
	
	public <T> void setValueAsync(String ref, T value, String priority, Runnable callback, Consumer<Throwable> errorHandler) {
		_setValueAsync(getReference(ref), value, priority, callback, errorHandler);
	}
	
	public <T> void setValueAsync(DatabaseReference ref, T value, Runnable callback, Consumer<Throwable> errorHandler) {
		_setValueAsync(ref, value, null, callback, errorHandler);
	}
	
	public <T> void setValueAsync(DatabaseReference ref, T value, Number priority, Runnable callback, Consumer<Throwable> errorHandler) {
		_setValueAsync(ref, value, priority, callback, errorHandler);
	}
	
	public <T> void setValueAsync(DatabaseReference ref, T value, String priority, Runnable callback, Consumer<Throwable> errorHandler) {
		_setValueAsync(ref, value, priority, callback, errorHandler);
	}
	
	private <T> void _setValueAsync(DatabaseReference ref, T value, Object priority, Runnable callback, Consumer<Throwable> errorHandler) {
		ApiFuture<Void> future = ref.setValueAsync(value, priority);
		ApiFutures.addCallback(future, new ApiFutureCallback<Void>() {
			@Override
			public void onFailure(Throwable t) {
				errorHandler.accept(t);
			}
			
			@Override
			public void onSuccess(Void result) {
				callback.run();
			}
		});
	}
	
	public void updateChildren(String ref, Map<String, Object> values) {
		updateChildren(getReference(ref), values);
	}
	
	public void updateChildrenAsync(String ref, Map<String, Object> values, Runnable callback) {
		updateChildrenAsync(getReference(ref), values, callback);
	}
	
	public void updateChildrenAsync(String ref, Map<String, Object> values, Runnable callback, Consumer<Throwable> errorHandler) {
		updateChildrenAsync(getReference(ref), values, callback, errorHandler);
	}
	
	public void updateChildren(DatabaseReference ref, Map<String, Object> values) {
		try {
			Semaphore sem = new Semaphore(1);
			sem.acquire();
			
			updateChildrenAsync(ref, values, () -> sem.release());
			
			sem.acquire();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void updateChildrenAsync(DatabaseReference ref, Map<String, Object> values, Runnable callback) {
		updateChildrenAsync(ref, values, callback, err -> {
			throw new RuntimeException(err);
		});
	}
	
	public void updateChildrenAsync(DatabaseReference ref, Map<String, Object> values, Runnable callback, Consumer<Throwable> errorHandler) {
		ApiFuture<Void> future = ref.updateChildrenAsync(values);
		ApiFutures.addCallback(future, new ApiFutureCallback<Void>() {
			@Override
			public void onFailure(Throwable t) {
				errorHandler.accept(t);
			}
			
			@Override
			public void onSuccess(Void result) {
				callback.run();
			}
		});
	}
	
	public <T> T getValue(String ref, Class<T> type) {
		return getValue(getReference(ref), type);
	}
	
	public <T> void getValueAsync(String ref, Class<T> type, Consumer<T> callback) {
		getValueAsync(getReference(ref), type, callback);
	}
	
	public <T> void getValueAsync(String ref, Class<T> type, Consumer<T> callback, Consumer<DatabaseError> errorHandler) {
		getValueAsync(getReference(ref), type, callback, errorHandler);
	}
	
	public <T> T getValue(Query ref, Class<T> type) {
		try {
			Semaphore sem = new Semaphore(1);
			sem.acquire();
			
			Vector<T> refObj = new Vector<>();
			getValueAsync(ref, type, e -> {
				refObj.add(e);
				sem.release();
			});
			
			sem.acquire();
			
			return refObj.get(0);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public <T> void getValueAsync(Query ref, Class<T> type, Consumer<T> callback) {
		getValueAsync(ref, type, callback, err -> {
			throw new RuntimeException(err.toException());
		});
	}
	
	public <T> void getValueAsync(Query ref, Class<T> type, Consumer<T> callback, Consumer<DatabaseError> errorHandler) {
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				callback.accept(snapshot.getValue(type));
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				errorHandler.accept(error);
			}
		});
	}
	
	public void removeValue(String ref) {
		removeValue(getReference(ref));
	}
	
	public void removeValueAsync(String ref, Runnable callback) {
		removeValueAsync(getReference(ref), callback);
	}
	
	public void removeValueAsync(String ref, Runnable callback, Consumer<Throwable> errorHandler) {
		removeValueAsync(getReference(ref), callback, errorHandler);
	}
	
	public void removeValue(DatabaseReference ref) {
		try {
			Semaphore sem = new Semaphore(1);
			sem.acquire();
			
			removeValueAsync(ref, () -> sem.release());
			
			sem.acquire();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void removeValueAsync(DatabaseReference ref, Runnable callback) {
		removeValueAsync(ref, callback, err -> {
			throw new RuntimeException(err);
		});
	}
	
	public void removeValueAsync(DatabaseReference ref, Runnable callback, Consumer<Throwable> errorHandler) {
		ApiFuture<Void> future = ref.removeValueAsync();
		ApiFutures.addCallback(future, new ApiFutureCallback<Void>() {
			@Override
			public void onFailure(Throwable t) {
				errorHandler.accept(t);
			}
			
			@Override
			public void onSuccess(Void result) {
				callback.run();
			}
		});
	}
	
	public static final class UpdateListener {
		
		private Query			   ref;
		private ValueEventListener listener;
		
		public UpdateListener(Query ref, ValueEventListener listener) {
			this.ref = ref;
			this.listener = listener;
		}
	}
	
	private final Vector<UpdateListener> updateListeners = new Vector<>();
	
	public <T> UpdateListener addUpdateListener(String ref, Class<T> type, Consumer<T> callback) {
		return addUpdateListener(getReference(ref), type, callback);
	}
	
	public <T> UpdateListener addUpdateListener(String ref, Class<T> type, Consumer<T> callback, Consumer<DatabaseError> errorHandler) {
		return addUpdateListener(getReference(ref), type, callback, errorHandler);
	}
	
	public <T> UpdateListener addUpdateListener(Query ref, Class<T> type, Consumer<T> callback) {
		return addUpdateListener(ref, type, callback, DEFAULT_DBERR_HANDLER);
	}
	
	public <T> UpdateListener addUpdateListener(Query ref, Class<T> type, Consumer<T> callback, Consumer<DatabaseError> errorHandler) {
		ValueEventListener eventListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				callback.accept(snapshot.getValue(type));
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				errorHandler.accept(error);
			}
		};
		
		ref.addValueEventListener(eventListener);
		
		UpdateListener listener = new UpdateListener(ref, eventListener);
		updateListeners.add(listener);
		return listener;
	}
	
	public void removeUpdateListener(UpdateListener listener) {
		listener.ref.removeEventListener(listener.listener);
		updateListeners.remove(listener);
	}
	
	public void removeAllUpdateListeners() {
		updateListeners.forEach(listener -> {
			listener.ref.removeEventListener(listener.listener);
		});
		updateListeners.removeAllElements();
	}
	
	public static final class ChildUpdateListener {
		
		private Query			   ref;
		private ChildEventListener listener;
		
		public ChildUpdateListener(Query ref, ChildEventListener listener) {
			this.ref = ref;
			this.listener = listener;
		}
	}
	
	private final Vector<ChildUpdateListener> childUpdateListeners = new Vector<>();
	
	public static enum EventType {
		REMOVE,
		MOVE,
		CHANGE,
		ADD
	}
	
	public <T> ChildUpdateListener addChildUpdateListener(String ref, Class<T> type, BiConsumer<T, EventType> callback) {
		return addChildUpdateListener(getReference(ref), type, callback);
	}
	
	public <T> ChildUpdateListener addChildUpdateListener(String ref, Class<T> type, BiConsumer<T, EventType> callback, Consumer<DatabaseError> errorHandler) {
		return addChildUpdateListener(getReference(ref), type, callback, errorHandler);
	}
	
	public <T> ChildUpdateListener addChildUpdateListener(Query ref, Class<T> type, BiConsumer<T, EventType> callback) {
		return addChildUpdateListener(ref, type, callback, DEFAULT_DBERR_HANDLER);
	}
	
	public <T> ChildUpdateListener addChildUpdateListener(Query ref, Class<T> type, BiConsumer<T, EventType> callback, Consumer<DatabaseError> errorHandler) {
		ChildEventListener eventListener = new ChildEventListener() {
			@Override
			public void onChildRemoved(DataSnapshot snapshot) {
				callback.accept(snapshot.getValue(type), EventType.REMOVE);
			}
			
			@Override
			public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
				callback.accept(snapshot.getValue(type), EventType.MOVE);
			}
			
			@Override
			public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
				callback.accept(snapshot.getValue(type), EventType.CHANGE);
			}
			
			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
				callback.accept(snapshot.getValue(type), EventType.ADD);
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				errorHandler.accept(error);
			}
		};
		
		ref.addChildEventListener(eventListener);
		
		ChildUpdateListener listener = new ChildUpdateListener(ref, eventListener);
		childUpdateListeners.add(listener);
		return listener;
	}
	
	public void removeChildUpdateListener(ChildUpdateListener listener) {
		listener.ref.removeEventListener(listener.listener);
		childUpdateListeners.remove(listener);
	}
	
	public void removeAllChildUpdateListeners() {
		childUpdateListeners.forEach(listener -> {
			listener.ref.removeEventListener(listener.listener);
		});
		childUpdateListeners.removeAllElements();
	}
	
	public void setPriority(String ref, Number priority) {
		_setPriority(getReference(ref), priority);
	}
	
	public void setPriority(String ref, String priority) {
		_setPriority(getReference(ref), priority);
	}
	
	public void setPriority(DatabaseReference ref, Number priority) {
		_setPriority(ref, priority);
	}
	
	public void setPriority(DatabaseReference ref, String priority) {
		_setPriority(ref, priority);
	}
	
	private void _setPriority(DatabaseReference ref, Object priority) {
		try {
			Semaphore sem = new Semaphore(1);
			sem.acquire();
			
			_setPriorityAsync(ref, priority, () -> sem.release());
			
			sem.acquire();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setPriorityAsync(String ref, Number priority, Runnable callback) {
		_setPriorityAsync(getReference(ref), priority, callback);
	}
	
	public void setPriorityAsync(String ref, String priority, Runnable callback) {
		_setPriorityAsync(getReference(ref), priority, callback);
	}
	
	public void setPriorityAsync(DatabaseReference ref, Number priority, Runnable callback) {
		_setPriorityAsync(ref, priority, callback);
	}
	
	public void setPriorityAsync(DatabaseReference ref, String priority, Runnable callback) {
		_setPriorityAsync(ref, priority, callback);
	}
	
	private void _setPriorityAsync(DatabaseReference ref, Object priority, Runnable callback) {
		_setPriorityAsync(ref, priority, callback, DEFAULT_ERR_HANDLER);
	}
	
	public void setPriorityAsync(String ref, Number priority, Runnable callback, Consumer<Throwable> errorHandler) {
		_setPriorityAsync(getReference(ref), priority, callback, errorHandler);
	}
	
	public void setPriorityAsync(String ref, String priority, Runnable callback, Consumer<Throwable> errorHandler) {
		_setPriorityAsync(getReference(ref), priority, callback, errorHandler);
	}
	
	public void setPriorityAsync(DatabaseReference ref, Number priority, Runnable callback, Consumer<Throwable> errorHandler) {
		_setPriorityAsync(ref, priority, callback, errorHandler);
	}
	
	public void setPriorityAsync(DatabaseReference ref, String priority, Runnable callback, Consumer<Throwable> errorHandler) {
		_setPriorityAsync(ref, priority, callback, errorHandler);
	}
	
	private void _setPriorityAsync(DatabaseReference ref, Object priority, Runnable callback, Consumer<Throwable> errorHandler) {
		ApiFuture<Void> future = ref.setPriorityAsync(priority);
		ApiFutures.addCallback(future, new ApiFutureCallback<Void>() {
			@Override
			public void onFailure(Throwable t) {
				errorHandler.accept(t);
			}
			
			@Override
			public void onSuccess(Void result) {
				callback.run();
			}
		});
	}
}
