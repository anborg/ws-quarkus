package service.cache;

import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Optional;

public interface  CacheService<K,V> {
    Optional<V> get(K key);

    Pair<K,V> put(K key, V val);

    Uni<Void> del(K key);

    Uni<List<V>> keys();
}
