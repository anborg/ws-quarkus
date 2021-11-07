package api;


import model.ResponseId;
import model.User;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import service.CisService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Tag(name = Api.users.tag)

@Path(Api.users.url) // /api/v1/asset/
@ApplicationScoped
@RolesAllowed(Api.adminuser.role)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private static final Logger log = Logger.getLogger(UserResource.class.getName());

    @Inject
    CisService service;

    @Operation(operationId = "userCreate",summary = "Create",description = "Do not provide id, addDate, modDate")
    @APIResponse(responseCode = Api.res.created.code, description = Api.res.created.desc)
    @APIResponse(responseCode = Api.res.bad_req.code, description = Api.res.bad_req.desc)
    @APIResponse(responseCode = Api.res.server_err.code, description = Api.res.server_err.desc)
    @POST
    @Transactional
    public Response create(@RequestBody(description = "new user", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = Api.workorders.newObject))//
                                           User in) {
        var invalidReq = checkInvalidUser(in);
        if(invalidReq.isPresent()) return invalidReq.get();

        try {
            service.create(in);
            return Response.ok(ResponseId.builder().id(in.id).build()).status(Response.Status.CREATED.getStatusCode()).build();
        }catch (Throwable t){
            var err = "Error creating user";
            log.log(Level.SEVERE, err, t);
            return Api.serverError(err);
        }
    }
    @Operation(operationId = "userResetPassword",summary = "ResetPassword",description = "Do not provide id, addDate, modDate")
    @APIResponse(responseCode = Api.res.ok.code, description = Api.res.ok.desc)
    @APIResponse(responseCode = Api.res.bad_req.code, description = Api.res.bad_req.desc)
    @APIResponse(responseCode = Api.res.server_err.code, description = Api.res.server_err.desc)
    @POST
    @Transactional
    public Response reset(@RequestBody(description = "new user", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = Api.workorders.newObject))//
                                   User in) {
        var invalidReq = checkInvalidUser(in);
        if(invalidReq.isPresent()) return invalidReq.get();

        try {
            service.resetPassword(in);
            return Response.ok(ResponseId.builder().id(in.id).build()).status(Response.Status.CREATED.getStatusCode()).build();
        }catch (Throwable t){
            var err = "Error creating user";
            log.log(Level.SEVERE, err, t);
            return Api.serverError(err);
        }
    }
    private java.util.Optional<Response> checkInvalidUser(User in) {
        final var errStr = "MUST ONLY provide {userName,password}. For pwd-reset MUST ONLY provide {userName,password,newPassword. Roles will never be handled here; contact support";
        if (Objects.nonNull(in.id) || Objects.isNull(in.username) || Objects.isNull(in.password)) {//NEVER id ,//MUST username, MUST PWD
            log.log(Level.SEVERE, errStr);
            return Optional.of(Api.badRequest(errStr));
        }
        return Optional.of(null);
    }

}