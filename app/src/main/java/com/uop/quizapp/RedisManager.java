public class RedisManager {
    private static RedisManager instance;
    private final java.util.Map<String, Object> store = new java.util.HashMap<>();

    private RedisManager() {}

    public static synchronized RedisManager getInstance() {
        if (instance == null) {
            instance = new RedisManager();
        }
        return instance;
    }

    public synchronized void put(String key, Object value) {
        store.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public synchronized <T> T get(String key) {
        return (T) store.get(key);
    }

    public synchronized void clear() {
        store.clear();
    }
}
