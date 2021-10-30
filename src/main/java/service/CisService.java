package service;

import model.Workorder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class CisService {
    private static final Logger log = Logger.getLogger(CisService.class.getName());

    @Inject
    EntityManager em;

    //TODO change time to Instant
    public List<Workorder> getActionable(Instant sinceDate) {
        TypedQuery<Workorder> query = em.createNamedQuery("WorkOrder.findActionable", Workorder.class);
        var out = query.setParameter("sinceDate", sinceDate)
                .getResultList();
        return out;
    }

//    //WARNING: DO NOT add @Transactional here
//    //Call in two legs - as addDt from dbtrigger is not showing up from in saved object.
//    public Workorder persist(Workorder in){//This
//        var out1 = persist1(in) ;
//        var out2 = byId(out1.id);
//        return out2;
//    }
    @Transactional
    public Workorder persist(Workorder in) {
        em.persist(in);
        em.flush();
        return in;
    }
    @Transactional
    public Optional<Workorder> byId(Long id){
        TypedQuery<Workorder> query = em.createNamedQuery("WorkOrder.findById", Workorder.class);
//        var out = query.setParameter("id", id).getResultList();
        var out = query.setParameter("id", id).getResultList().stream().findFirst();
        return out;
    }

    //WARNING: DO NOT add @Transactional here
    public Workorder associateEamId(Long id, String eamId) {
        var inOpt = byId(id);
        if(inOpt.isEmpty()){
            log.severe("Invalid CIS Workorder id. Should not reach service layer.  Id="+ id);
            return null;
        }
        var in = inOpt.get();
        in.eamId = eamId;
        var out = persist(in);
        return out;
    }

    public Workorder updateStatus(Long id, String status) {
        var inOpt = byId(id);
        if(inOpt.isEmpty()){
            log.severe("Invalid CIS Workorder id. Should not reach service layer.  Id="+ id);
            return null;
        }
        var in = inOpt.get();
        in.status = status;
        var out = persist(in);
        return out;
    }
}
