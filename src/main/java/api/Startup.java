package api;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;
import model.User;
import service.repo.UserRepo;


@Singleton
public class Startup {

    @Inject
    UserRepo repo;
    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        // reset and load all test users
        repo.deleteAll();
        repo.add("admin", "admin", "admin");
        repo.add("lucitest", "luci123", "apiuser");
    }
}