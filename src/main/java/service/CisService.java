package service;

import api.PageRequest;
import api.PageResults;
import model.Workorder;
import service.repo.WorkorderRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class CisService {
    private static final Logger log = Logger.getLogger(CisService.class.getName());

//    @Inject
//    EntityManager em;

    @Inject
    WorkorderRepo repo;

    public PageResults<Workorder> getActionable(Instant sinceDate, PageRequest pageRequest) {
        var out = repo.findActionable(sinceDate,pageRequest);
        return out;
    }


    @Transactional
    public Workorder save(Workorder in) {
        repo.persistAndFlush(in);
        return in;
    }
    @Transactional
    public Optional<Workorder> byId(Long id){
        var out = repo.findByIdOptional(id);
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
        var out = save(in);
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
        var out = save(in);
        return out;
    }
}
