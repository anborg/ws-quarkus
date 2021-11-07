package api;


import model.PageRequest;
import model.PageResults;
import model.ResponseId;
import model.Workorder;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import service.CisService;
import util.Util;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Tag(name = Api.workorders.tag)

@Path(Api.workorders.url) // /api/v1/asset/
@ApplicationScoped
@RolesAllowed(Api.apiuser.role)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkorderResource {
    private static final Logger log = Logger.getLogger(WorkorderResource.class.getName());

    @Inject
    CisService service;

    @Operation(operationId = "workorderCreate",summary = "Create",description = "Do not provide id, addDate, modDate")
    @APIResponse(responseCode = Api.res.created.code, description = Api.res.created.desc)
    @APIResponse(responseCode = Api.res.bad_req.code, description = Api.res.bad_req.desc)
    @APIResponse(responseCode = Api.res.server_err.code, description = Api.res.server_err.desc)
//    @APIResponse(Api.response.bad_request) //TODO how?
    @POST
    @Transactional
    public Response create(@RequestBody(description = "new work order", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = Api.workorders.newObject))//
                                       Workorder in) {
        final var errStr = "For creating: Do not set {id | addDate| modDate}. DB will allocate";
        if (Objects.nonNull(in.id) || Objects.nonNull(in.addDate) || Objects.nonNull(in.modDate)) {
            log.log(Level.SEVERE, errStr);
            return Api.badRequest(errStr);
        }
        try {
            service.save(in);
            return Response.ok(ResponseId.builder().id(in.id).build()).status(Response.Status.CREATED.getStatusCode()).build();
        }catch (Throwable t){
            var err = "Error creating object";
            log.log(Level.SEVERE, err, t);
            return Api.serverError(err);
        }
    }

    @Operation(operationId = "getById",summary = "Get",description = "get by id")
    @APIResponse(responseCode = Api.res.ok.code, description = Api.res.ok.desc)
    @APIResponse(responseCode = Api.res.not_found.code, description = Api.res.not_found.desc)
    @APIResponse(responseCode = Api.res.server_err.code, description = Api.res.server_err.desc)
    @GET
    @Path("{id}")
    public Response get( @Parameter(example = "1") @PathParam("id") Long id) {
        try {
            var out =  service.byId(id);
            if(out.isPresent()) {
                return Response.ok(out.get()).build();
            }else{
                var err = "Invlaid id. Who is scanning for ids? id=" + id;
                log.severe(err);
                return Api.notFound();
            }
        } catch (Throwable t) {
            var err = "Error fetching object";
            log.log(Level.SEVERE, err, t);
            return Api.serverError(err);
        }
    }

    @Operation(operationId = "linkEamId",summary = "Associate eamId",description = "For linking a cis-workorders to eam-workorders")
    @APIResponse(responseCode = Api.res.ok.code, description = Api.res.ok.desc)
    @APIResponse(responseCode = Api.res.not_found.code, description = Api.res.not_found.desc)
    @APIResponse(responseCode = Api.res.server_err.code, description = Api.res.server_err.desc)
    @PATCH
    @Path("{id}/eam/{eamId}")
    @Transactional
    public Response update(@Parameter(example = "1") @PathParam("id") @NotNull Long id
            ,@Parameter(example = "222") @PathParam("eamId")  @NotEmpty String eamId) {
        try {
            service.associateEamId(id, eamId);
            return Response.ok().build();
        } catch (Throwable t) {
            var err = "Error updating object";
            log.log(Level.SEVERE, err, t);
            return Api.serverError(err);
        }
    }
    @Operation(operationId = "updateStatus",summary = "Status update", description = "When eam-Workorder is completed, use this to update status")
    @APIResponse(responseCode = Api.res.ok.code, description = Api.res.ok.desc)
    @APIResponse(responseCode = Api.res.not_found.code, description = Api.res.not_found.desc)
    @APIResponse(responseCode = Api.res.server_err.code, description = Api.res.server_err.desc)
    @PATCH
    @Path("{id}/status/{status}")
    @Transactional
    public Response updateStatus(@Parameter(example = "1") @PathParam("id") @NotNull Long id
            ,@Parameter(example = "P")@PathParam("status") @NotEmpty String status) {
        try {
            service.updateStatus(id, status);
            return Response.ok().build();
        }  catch (Throwable t) {
            var err = "Error updating object";
            log.log(Level.SEVERE, err, t);
            return Api.serverError(err);
        }
    }

    @Operation(operationId = "getActionable", summary = "Search for", description = "Workorders that need to be acted upon, e.g new/pending")
    @APIResponse(responseCode = Api.res.ok.code, description = Api.res.ok.desc)
    @APIResponse(responseCode = Api.res.bad_req.code, description = Api.res.bad_req.desc)
    @APIResponse(responseCode = Api.res.server_err.code, description = Api.res.server_err.desc)
    @GET
    @Path("actionable/since/{createDate}")
    public Response getActionable(@DefaultValue("2021-10-30")@PathParam("createDate") String sinceDateString, @BeanParam PageRequest pageRequest) {//, @BeanParam PageRequest pageRequest
        Instant sinceDate;
        try {
            sinceDate = Util.validateSearchDate(sinceDateString);
        } catch (Throwable t) {
            var err = "Query date must be with past 6 months. Format allowed: yyyy-MM-dd.  Specific Error: " + t.getLocalizedMessage();
            log.log(Level.SEVERE, err);//Removing stacktrace. no need for this stacktracee in log
            return Api.badRequest(err);
        }

        PageResults<Workorder> out = service.getActionable(sinceDate,pageRequest);
        return Response.ok(out).build();
    }
}