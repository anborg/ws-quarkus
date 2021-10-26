package api;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import model.WorkOrder;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Path("/api/v1/asset/workorders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Work Orders")
@ApplicationScoped
public class WorkOrderResource {

    private static final Logger LOGGER = Logger.getLogger(WorkOrderResource.class.getName());
    static final  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Inject
    EntityManager em;

    @GET
    @Path("actionable/since/{createDate}")
    @Blocking
    public Response getActionable(@PathParam("createDate") String sinceDateString) {
        try {
            Instant sinceDate = LocalDate.parse(sinceDateString, formatter).atStartOfDay(ZoneId.of( "America/Montreal" )).toInstant();
        }catch(Throwable t){
            var errStr = "Valid date format is yyyy-MM-dd. Invalid date : " + t.getLocalizedMessage();
            //TODO log error
            throw new WebApplicationException(errStr);
        }
        System.out.println("Fetch WO since sinceDateString :" + sinceDateString);
        TypedQuery<WorkOrder> query =  em.createNamedQuery("WorkOrder.findAll", WorkOrder.class);
        var out =  query.setParameter("sinceDate", sinceDateString)
                .getResultList();
        return Response.ok(out).build();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long id) {
        var out =  em.find(WorkOrder.class, id);
        if(Objects.nonNull(out)) {
            return Response.ok(out).build();
        }else{
            return Response.noContent().build();
        }
    }

    @POST
    @Transactional
    public Response create(WorkOrder in) {
        if (in.id != null) {
            throw new WebApplicationException("Do not set id, for creating workorder. DB will allocate id", 422);
        }
        em.persist(in);
        return Response.ok(in).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public WorkOrder update(@PathParam("id") Long id, WorkOrder in) {
        if (in.comments == null) {
            throw new WebApplicationException("Fruit Name was not set on request.", 422);
        }

        var entity = em.find(WorkOrder.class, id);

        if (entity == null) {
            throw new WebApplicationException("WorkOrder with id of " + id + " does not exist.", 404);
        }

        entity.comments = in.comments;
        entity.status = in.status;
        return entity;
    }

//    @DELETE
//    @Path("{id}")
//    @Transactional
    public Response delete(@PathParam("id") Long id) {
        var entity = em.getReference(WorkOrder.class, id);
        if (entity == null) {
            throw new WebApplicationException("WorkOrder with id of " + id + " does not exist.", 404);
        }
        em.remove(entity);
        return Response.status(204).build();
    }
}