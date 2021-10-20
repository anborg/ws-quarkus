package api;

import model.WorkOrder;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Path("/api/v1/asset/workorders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "asset-dWorkOrdero")
@ApplicationScoped
public class AssetResource {

    private static final Logger LOGGER = Logger.getLogger(AssetResource.class.getName());

//    @Inject
//    AssetServiceOracle service;

    @Inject
    EntityManager em;

    @GET
    public List<WorkOrder> getAll() {
//        return service.getAll();
        return em.createNamedQuery("WorkOrder.findAll", WorkOrder.class)
                .getResultList();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Integer id) {
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
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        em.persist(in);
        return Response.ok(in).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public WorkOrder update(@PathParam("id") Integer id, WorkOrder in) {
        if (in.description == null) {
            throw new WebApplicationException("Fruit Name was not set on request.", 422);
        }

        var entity = em.find(WorkOrder.class, id);

        if (entity == null) {
            throw new WebApplicationException("Fruit with id of " + id + " does not exist.", 404);
        }

        entity.description = in.description;
        entity.status = in.status;
        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id) {
        var entity = em.getReference(WorkOrder.class, id);
        if (entity == null) {
            throw new WebApplicationException("Fruit with id of " + id + " does not exist.", 404);
        }
        em.remove(entity);
        return Response.status(204).build();
    }
}