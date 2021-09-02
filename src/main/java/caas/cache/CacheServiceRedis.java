package caas.cache;
import io.quarkus.redis.client.RedisClient;
import io.vertx.redis.client.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class CacheServiceRedis implements CacheService {

    @Inject
    RedisClient redis;

    @Override
    public Optional<String> get(String key) {
        Response res = redis.get(key);
        Optional<String> result = Objects.isNull(res) ? Optional.empty() : Optional.of(res.toString());
        return result;
    }

    @Override
    public String put(String key, String val) {
        redis.set(Arrays.asList(key, val.toString()));
        return get(key).get();
    }
}
