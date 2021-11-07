package api;

import io.quarkus.runtime.StartupEvent;
import service.repo.UserRepo;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;


@Singleton
public class Startup {
    private static final String ADMIN_ROLES = String.join(",", Api.adminuser.role, Api.apiuser.role);

    @Inject
    UserRepo repo;
    @Transactional
    public void loadUsersForTest(@Observes StartupEvent evt) {//TODO Need to load only for non-PROD profiles
        repo.deleteAll();// reset and load all test users
        repo.addTestUserOnly(Api.adminuser.testuser, Api.adminuser.testpassword, ADMIN_ROLES);
        repo.addTestUserOnly(Api.apiuser.testuser, Api.apiuser.testpassword, Api.apiuser.role);
    }
}