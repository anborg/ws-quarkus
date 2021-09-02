package caas.cache;

import java.util.Optional;

public interface CacheService {
    Optional<String> get(String key);
    String put(String key, String val);
}
