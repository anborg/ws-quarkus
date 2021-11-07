package service.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import model.User;
import util.Util;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepo implements PanacheRepository<User> {

    public void addTestUserOnly(String username, String password, String role) {
        User user = new User();
        user.username = username;
        user.password = Util.hash(password);
        user.role = role;
        persist(user);
    }
}
