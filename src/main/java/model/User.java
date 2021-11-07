package model;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.*;


@Entity
@Table(name = "CIS_API_USER")
@UserDefinition
public class User {//extends PanacheEntity {
    @Id
    @GeneratedValue(generator = "SEQ_CIS_API_USER")
    public Long id;
    @Username
    @Column(unique=true)
    public String username;
    @Password
    public String password;
    @Roles
    public String role;//csv of roles

}