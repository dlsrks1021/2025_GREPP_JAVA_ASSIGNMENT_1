package framework;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private Map<String, Object> attributes = new HashMap<>();

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void invalidate() {
        attributes.clear();
    }
}
