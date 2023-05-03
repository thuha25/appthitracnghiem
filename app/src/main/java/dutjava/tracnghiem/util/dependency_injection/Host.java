package dutjava.tracnghiem.util.dependency_injection;

public interface Host {
    Class<?> getClass(Object key);
    <T> T get(Object key);
    boolean isPresent(Object key);
    void register(Object key, Object object);
    void registerClass(Object key, Class<?> clazz);
}