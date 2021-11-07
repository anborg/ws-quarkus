package api;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path(Api.health.url)
@Tag(name = Api.health.tag)
@ApplicationScoped
@Produces(MediaType.TEXT_PLAIN)
public class HealthResource {

    @GET
    @PermitAll
    public String hello(){
        return "Hello Anon";
    }
    @GET
    @Path("secure")
    @RolesAllowed(Api.apiuser.role)
    public String helloSecure(@Context SecurityContext securityContext){
        return "Secure-hello to user: "+ securityContext.getUserPrincipal().getName();
    }
}
