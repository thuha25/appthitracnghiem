package dutjava.tracnghiem.util.dependency_injection;

import java.util.HashMap;

class HostImpl implements Host {
    private HashMap<Object, Object> container = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Object key) {
        if(!container.containsKey(key))
            throw new RuntimeException("Object with key: " + key + " is not registered");
        return (T) container.get(key);
    }

    @Override
    public boolean isPresent(Object key) {
        return container.containsKey(key);
    }

    @Override
    public void register(Object key, Object object) {
        container.put(key, object);
    }
}
