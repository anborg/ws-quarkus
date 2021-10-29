package api;

import io.smallrye.common.annotation.Blocking;
import model.Workorder;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
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

    @POST
    @Transactional
    public Response create(Workorder in) {
        final var errStr = "For creating: Do not set {id | addDate| modDate}. DB will allocate";
        if (Objects.nonNull(in.id) || Objects.nonNull(in.addDate) || Objects.nonNull(in.modDate)) {
            Util.badRequest(errStr);
        }
        service.persist(in);
        return Response.ok(in).status(Response.Status.CREATED.getStatusCode()).build();
    }
    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long id) {
        var out =  service.byId(id);
        if(Objects.nonNull(out)) {
            return Response.ok(out).build();
        }else{
            return Response.noContent().build();
        }
    }

    @PUT
    @Path("{id}/eam/{eamId}")
    @Transactional
    public Response update(@PathParam("id") Long id,@PathParam("eamId") String eamId) {
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
        return Response.ok(out).build();
    }

    @GET
    @Path("actionable/since/{createDate}")
    @Blocking
    public Response getActionable(@PathParam("createDate") String sinceDateString) {
        try {
            Instant sinceDate = LocalDate.parse(sinceDateString, formatter).atStartOfDay(ZoneId.of("America/Montreal")).toInstant();
        } catch (Throwable t) {
            var errStr = "Valid date format is yyyy-MM-dd. Invalid date : " + t.getLocalizedMessage();
            log.severe(errStr);
            return Util.badRequest(errStr);
        }
        System.out.println("Fetch WO since sinceDateString :" + sinceDateString);

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