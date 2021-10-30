package api;

import io.smallrye.common.annotation.Blocking;
import model.Id;
import model.Workorder;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import service.CisService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Path("/api/v1/asset/workorders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Work Orders")
@ApplicationScoped
public class WorkorderResource {
    private static final Logger log = Logger.getLogger(WorkorderResource.class.getName());
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Inject
    CisService service;

    @Operation(summary = "Create",description = "Do not provide id, addDate, modDate")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Server Error")
    @POST
    @Transactional
    public Response create(@RequestBody(description = "new work order", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = Apidocutil.workorder.newObject))//
                                       Workorder in) {
        final var errStr = "For creating: Do not set {id | addDate| modDate}. DB will allocate";
        if (Objects.nonNull(in.id) || Objects.nonNull(in.addDate) || Objects.nonNull(in.modDate)) {
            Util.badRequest(errStr);
        }
        service.persist(in);
        return Response.ok(Id.builder().id(in.id).build()).status(Response.Status.CREATED.getStatusCode()).build();
    }
    @GET
    @Path("{id}")
    @Operation(summary = "Get",description = "get by id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @APIResponse(responseCode = "500", description = "Server Error")
    public Response get(
            @Parameter(example = "1")
            @PathParam("id") Long id) {
        var out =  service.byId(id);
        if(Objects.nonNull(out)) {
            return Response.ok(out).build();
        }else{
            return Util.notFound();
        }
    }

    @PATCH
    @Path("{id}/eam/{eamId}")
    @Transactional
    @Operation(summary = "Associate eamId",description = "For linking a cis-workorder to eam-workorder")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "404", description = "Not Found")
    @APIResponse(responseCode = "500", description = "Server Error")
    public Response update(@Parameter(example = "1") @PathParam("id") Long id
            ,@Parameter(example = "222") @PathParam("eamId") String eamId) {
        if (Objects.isNull(id) || Objects.isNull(eamId)) {
            final var err  = "Invalid input params id / eamId";
            log.severe(err);
            return Util.badRequest(err);
        }
        final var out = service.associateEamId(id, eamId);
        if (Objects.isNull(out)) {
            final var err = "Not found. Find who is sending invalid id";
            log.warning(err);
            return Util.notFound();
        }
        return Response.ok().build();
    }
    @PATCH
    @Path("{id}/status/{status}")
    @Transactional
    @Operation(summary = "Status update", description = "When eam-Workorder is completed, use this to update status")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "404", description = "Not Found")
    @APIResponse(responseCode = "500", description = "Server Error")
    public Response updateStatus(@Parameter(example = "1") @PathParam("id") Long id
            ,@Parameter(example = "P")@PathParam("status") String status) {
        if (Objects.isNull(id) || Objects.isNull(status)) {
            final var err  = "Invalid input params id / status";
            log.severe(err);
            return Util.badRequest(err);
        }
        final var out = service.updateStatus(id, status);
        if (Objects.isNull(out)) {
            final var err = "Not found. Find who is sending invalid id";
            log.warning(err);
            return Util.notFound();
        }
        return Response.ok().build();
    }
    @GET
    @Path("actionable/since/{createDate}")
    @Blocking
    @Operation(summary = "Search for", description = "Workorders that need to be acted upon, e.g new/pending")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Server Error")
    public Response getActionable(@PathParam("createDate") String sinceDateString) {
        try {
            Instant sinceDate = LocalDate.parse(sinceDateString, formatter).atStartOfDay(ZoneId.of("America/Montreal")).toInstant();
        } catch (Throwable t) {
            var errStr = "Valid date format is yyyy-MM-dd. Invalid date : " + t.getLocalizedMessage();
            log.severe(errStr);
            return Util.badRequest(errStr);
        }
        log.info("Fetch WO since sinceDateString :" + sinceDateString);

        List<Workorder> out = service.getActionable(sinceDateString);
        return Response.ok(out).build();
    }








}

//    @DELETE
//    @Path("{id}")
//    @Transactional
//    public Response delete(@PathParam("id") Long id) {
//        var entity = em.getReference(WorkOrder.class, id);
//        if (entity == null) {
//            throw new WebApplicationException("WorkOrder with id of " + id + " does not exist.", 404);
//        }
//        em.remove(entity);
//        return Response.status(204).build();
//    }