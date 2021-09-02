package caas;

import caas.cache.CacheService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/caas/redis/api/v1")
public class CachedemoResource {

    @Inject
    CacheService cache;

    @GET
    @Path("get/{key}")
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
    @Path("put/{key}/{value}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response get(@PathParam("key") String key,@PathParam("value") String value) {
        var retStr= cache.put(key,value);
        return Response.ok(retStr).build();
    }
}