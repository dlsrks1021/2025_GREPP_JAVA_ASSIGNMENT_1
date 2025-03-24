package framework;

import model.Grade;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private final Map<String, Object> attributes = new HashMap<>();

    public Session() {
        attributes.put("grade", Grade.ANONYMOUS);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void invalidate() {
        attributes.clear();
        attributes.put("grade", Grade.ANONYMOUS);
    }
}
