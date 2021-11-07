package service;

import io.quarkus.elytron.security.common.BcryptUtil;
import model.PageRequest;
import model.PageResults;
import model.User;
import model.Workorder;
import service.repo.UserRepo;
import service.repo.WorkorderRepo;
import util.Util;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class CisService {
    private static final Logger log = Logger.getLogger(CisService.class.getName());

    @Inject
    WorkorderRepo repo;
    @Inject
    UserRepo userRepo;
    @Transactional
    public void create(User in){
        User user = new User();
        user.username = in.username;
        user.password = BcryptUtil.bcryptHash(in.password);
        //user.role = role; //For security. Do not make this easy, let support guy insert role manually.
        userRepo.persist(user);
    }
    @Transactional
    public void resetPassword(User in){
        List<User> dbObjList = userRepo.list("username", in.username);
        if(Objects.isNull(dbObjList) || dbObjList.isEmpty()){
            log.severe("Attempt to reset pwd for a nonexisting username=" + in.username + ". Hint: Check if it is system problem, or hacking attempt");
            throw new ServiceException("Error resetting pwd for username: "+ in.username + ". Please contact support"); //Do not provide details
        }
        if(dbObjList.size() >1 ){
            log.severe("Potential flaw in XX USER table. Current design should not allow duplicate username in that table");
            throw new ServiceException("System flaw. Contact support");//DO not reveal what flaw.
        }
        User onlyExistingUser = dbObjList.get(0);
        onlyExistingUser.password = Util.hash(in.password);
        userRepo.persist(onlyExistingUser);
    }

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
    @Transactional
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
    @Transactional
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
