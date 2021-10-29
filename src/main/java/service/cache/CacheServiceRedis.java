package service.cache;
//import io.quarkus.redis.client.RedisClient;
//import io.quarkus.redis.client.reactive.ReactiveRedisClient;
//import io.smallrye.mutiny.Uni;
//import io.vertx.redis.client.Response;
//import io.vertx.mutiny.redis.client.Response;

import javax.inject.Singleton;

@Singleton
public class CacheServiceRedis {// implements CacheService<String, String> {
//
//    @Inject
//    RedisClient redis;
//    @Inject
//    ReactiveRedisClient redisRx;
//
//    @Override
//    public Optional<String> get(String key) {
//        Response res = redis.get(key);
//        Optional<String> result = Objects.isNull(res) ? Optional.empty() : Optional.of(res.toString());
//        return result;
//    }
//
//    @Override
//    public Pair<String,String> put(String key, String val) {
//        redis.set(Arrays.asList(key, val.toString()));
//        var val2 = get(key).get();
//        return Pair.of(key, val2);
//    }
//
//    @Override
//    public Uni<Void> del(String key) {
//        return redisRx.del(Arrays.asList(key))
//                .map(response -> null);
//    }
//    @Override
//    public Uni<List<String>> keys() {
//        return redisRx
//                .keys("*")
//                .map(response -> {
//                    List<String> result = new ArrayList<>();
//                    for (io.vertx.mutiny.redis.client.Response r : response) {
//                        result.add(r.toString());
//                        System.out.println(r.toString());
//                    }
//                    return result;
//                });
//    }
}
