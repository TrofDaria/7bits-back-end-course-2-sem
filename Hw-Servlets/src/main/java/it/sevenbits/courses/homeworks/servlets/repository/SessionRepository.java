package it.sevenbits.courses.homeworks.servlets.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionRepository {
    private static SessionRepository instance;
    private Map<UUID, String> sessions;

    private SessionRepository(){
        sessions = new HashMap<UUID, String>();
    }

    public static SessionRepository getInstance() {
        if (instance == null) {
            instance = new SessionRepository();
        }
        return instance;
    }

    public String getSession(UUID id){
        return sessions.get(id);
    }

    public UUID createSession(String name){
        UUID id = UUID.randomUUID();
        sessions.put(id,name);

        return id;
    }
}
