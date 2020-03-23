package sample.java.util.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    private Map<String, List<EventListener>> eventListeners = new HashMap<>();

    public EventManager(String... operations) {
        for (String operation : operations) {
            eventListeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(EventListener listener, String... eventTypes) {
        for (String eventType : eventTypes) {
            eventListeners.get(eventType).add(listener);
        }
    }

    public void unsubscribe(EventListener listener, String... eventTypes) {
        for (String eventType : eventTypes) {
            eventListeners.get(eventType).remove(listener);
        }
    }

    public void notify(String eventType) {
        for (EventListener listener : eventListeners.get(eventType)) {
            listener.onEvent(eventType);
        }
    }
}