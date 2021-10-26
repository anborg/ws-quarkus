package api;

//import org.eclipse.microprofile.openapi.annotations.tags.Tag;
//import service.cache.CacheService;
//import service.cache.Pair;
//import io.smallrye.mutiny.Uni;
//
//import javax.inject.Inject;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.util.List;

//@Path("/api/v1/redis/objects")
//@Produces(MediaType.APPLICATION_JSON)
//@Tag(name = "cache-demo")
public class CachedemoResource {
/*
    @Inject
    CacheService<String,String> cache;

    @GET
    @Path("{key}")
    public Response get(@PathParam("key") String key) {
        var result= cache.get(key);
        if(result.isPresent()) {
            return Response.ok(Pair.of(key,result.get())).build();
        }else{
            return Response.noContent().build();
        }
    }
    @PUT
    @Path("{key}/{value}")
    public Response get(@PathParam("key") String key,@PathParam("value") String value) {
        Pair<String,String> pair= cache.put(key,value);
        return Response.ok((pair)).build();
    }

    @GET
    @Path("/")

    public Uni<List<String>> keys() {
        return cache.keys();
    }

    @DELETE
    @Path("{key}")
    public Uni<Void> delete(@PathParam("key") String key) {
        return cache.del(key);
    }*/
}