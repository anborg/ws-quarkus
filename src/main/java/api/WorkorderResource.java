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
import service.ServiceException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
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
            log.log(Level.SEVERE, errStr);
            return Util.badRequest(errStr);
        }
        try {
            service.persist(in);
            return Response.ok(Id.builder().id(in.id).build()).status(Response.Status.CREATED.getStatusCode()).build();
        }catch (Throwable t){
            var err = "Error creating object";
            log.log(Level.SEVERE, err, t);
            return Util.serverError(err);
        }
    }
    @GET
    @Path("{id}")
    @Operation(summary = "Get",description = "get by id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @APIResponse(responseCode = "500", description = "Server Error")
    public Response get( @Parameter(example = "1") @PathParam("id") Long id) {
        try {
            var out =  service.byId(id);
            if(out.isPresent()) {
                return Response.ok(out.get()).build();
            }else{
                var err = "Invlaid id. Who is scanning for ids? id=" + id;
                log.severe(err);
                return Util.notFound();
            }
        } catch (Throwable t) {
            var err = "Error fetching object";
            log.log(Level.SEVERE, err, t);
            return Util.serverError(err);
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
    public Response update(@Parameter(example = "1") @PathParam("id") @NotNull Long id
            ,@Parameter(example = "222") @PathParam("eamId")  @NotEmpty String eamId) {
        try {
            service.associateEamId(id, eamId);
            return Response.ok().build();
        } catch (Throwable t) {
            var err = "Error updating object";
            log.log(Level.SEVERE, err, t);
            return Util.serverError(err);
        }
    }
    @PATCH
    @Path("{id}/status/{status}")
    @Transactional
    @Operation(summary = "Status update", description = "When eam-Workorder is completed, use this to update status")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "404", description = "Not Found")
    @APIResponse(responseCode = "500", description = "Server Error")
    public Response updateStatus(@Parameter(example = "1") @PathParam("id") @NotNull Long id
            ,@Parameter(example = "P")@PathParam("status") @NotEmpty String status) {
        try {
            service.updateStatus(id, status);
            return Response.ok().build();
        }  catch (Throwable t) {
            var err = "Error updating object";
            log.log(Level.SEVERE, err, t);
            return Util.serverError(err);
        }
    }
    @GET
    @Path("actionable/since/{createDate}")
    @Blocking
    @Operation(summary = "Search for", description = "Workorders that need to be acted upon, e.g new/pending")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Server Error")
    public Response getActionable(@PathParam("createDate") String sinceDateString) {
        Instant sinceDate = null;
        try {
            sinceDate = validateSearchDate(sinceDateString);
        } catch (Throwable t) {
            var err = "Invalid date. Allowed format is yyyy-MM-dd. Error: " + t.getLocalizedMessage();
            log.log(Level.SEVERE, err, t);
            return Util.badRequest(err);
        }

        List<Workorder> out = service.getActionable(sinceDate);
        return Response.ok(out).build();
    }

    public static Instant validateSearchDate(String sinceDateString){
        final int minDays = - 6*30, maxDays = 0;
        final var errDays =  "Query date should be between minDays="+ minDays +", maxDays="+maxDays;
        //
        var localDate = LocalDate.parse(sinceDateString, formatter);
        var sinceDate = localDate.atStartOfDay(ZoneId.of("America/Montreal")).toInstant();
        var now = LocalDate.now();
        long dayDiff = ChronoUnit.DAYS.between(now, localDate);
        if(dayDiff < minDays || dayDiff > maxDays) throw new ServiceException(errDays +". dayDiff="+dayDiff+ ". createDate/sinceDate ="+sinceDateString);
        return sinceDate;
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