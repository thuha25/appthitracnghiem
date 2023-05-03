package dutjava.tracnghiem.util.dependency_injection;

public interface Host {
    <T> T get(Object key);
    boolean isPresent(Object key);
    void register(Object key, Object object);
}