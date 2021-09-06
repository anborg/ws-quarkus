package caas;

import caas.cache.CacheService;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/caas/redis/api/v1/objects")
public class CachedemoResource {

    @Inject
    CacheService cache;

    @GET
    @Path("{key}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response get(@PathParam("key") String key) {
        var result= cache.get(key);
        if(result.isPresent()) {
            return Response.ok(result.get()).build();
        }else{
            return Response.noContent().build();
        }
    }
    @PUT
    @Path("{key}/{value}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response get(@PathParam("key") String key,@PathParam("value") String value) {
        var retStr= cache.put(key,value);
        return Response.ok(retStr).build();
    }

    @GET
    public Uni<List<String>> keys() {
        return cache.keys();
    }

    @DELETE
    @Path("{key}")
    public Uni<Void> delete(@PathParam("key") String key) {
        return cache.del(key);
    }
}