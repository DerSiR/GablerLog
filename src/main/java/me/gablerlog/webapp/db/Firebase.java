package me.gablerlog.webapp.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import elemental.json.Json;
import elemental.json.JsonObject;

public class Firebase {
	
	private final RealtimeDatabase database;
	
	private static final Map<String, Firebase> connections = new HashMap<>();
	
	private Firebase(String projectId, String serviceAccount) throws IOException {
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(
						IOUtils.toInputStream(serviceAccount, StandardCharsets.UTF_8)))
				.setDatabaseUrl("https://" + projectId + ".firebaseio.com")
				.build();
		
		FirebaseApp app = FirebaseApp.initializeApp(options);
		
		database = new RealtimeDatabase(FirebaseDatabase.getInstance(app));
	}
	
	public static Firebase open(String serviceAccountName) throws IOException {
		String serviceAccount = IOUtils.toString(
				Firebase.class.getResourceAsStream(serviceAccountName + ".json"),
				StandardCharsets.UTF_8);
		JsonObject serviceAccountJson = Json.parse(serviceAccount);
		String projectId = serviceAccountJson.getString("project_id");
		
		if (connections.containsKey(projectId)) {
			return connections.get(projectId);
		}
		
		Firebase firebase = new Firebase(projectId, serviceAccount);
		connections.put(projectId, firebase);
		return firebase;
	}
	
	public RealtimeDatabase getRealtimeDatabase() {
		return database;
	}
}
