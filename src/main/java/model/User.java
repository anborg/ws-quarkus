package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;


@Entity
@Table(name = "CIS_API_USER")
@UserDefinition
public class User {//extends PanacheEntity {
    @Id
    @GeneratedValue(generator = "SEQ_CIS_API_USER")
    public Long id;
    @Username
    public String username;
    @Password
    public String password;
    @Roles
    public String role;//csv of roles

}