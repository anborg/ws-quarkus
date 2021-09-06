package caas.cache;

import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Optional;

public interface CacheService {
    Optional<String> get(String key);
    String put(String key, String val);

    Uni<Void> del(String key);

    Uni<List<String>> keys();
}
