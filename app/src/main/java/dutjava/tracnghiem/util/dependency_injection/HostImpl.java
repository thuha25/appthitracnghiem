package dutjava.tracnghiem.util.dependency_injection;

import java.util.HashMap;

class HostImpl implements Host {
    private HashMap<Object, Object> container = new HashMap<>();
    private HashMap<Object, Class<?>> classDataContainer = new HashMap<>();

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

    @Override
    public Class<?> getClass(Object key) {
        return classDataContainer.get(key);
    }

    @Override
    public void registerClass(Object key, Class<?> clazz) {
        classDataContainer.put(key, clazz);
    }
}
