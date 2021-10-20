package service.asset;

import model.WorkOrder;
import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import org.hibernate.jdbc.Work;
import repo.Repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AssetServiceOracle {

//    @Inject
//    Repository<WorkOrder> repository;


    @Inject
//    @PersistenceUnit("users")
    EntityManager em;

    @Transactional
    public void create(String description) {
        WorkOrder wo = new WorkOrder();
        wo.description = description;
        em.persist(wo);
    }

//    @Inject
//    @DataSource("asset")
//    AgroalDataSource usersDataSource;

    public Optional<WorkOrder> get(Integer id){
        return Optional.of(null);
    }

    public List<WorkOrder> getAll() {
        return em.createNamedQuery("WorkOrder.findAll", WorkOrder.class)
                .getResultList();
    }
}
