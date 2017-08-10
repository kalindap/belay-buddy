package org.launchcode.belaybuddy.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by kalindapiper on 7/12/17.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private int id;

    @NotEmpty(message = "Name is a required field")
    @Size(min=1, max=15, message = "Name must be between 1 and 15 characters")
    private String firstName;

    @NotEmpty(message = "Name is a required field")
    @Size(min=2, max=20, message = "Name must be between 2 and 20 characters")
    private String lastName;

    @NotEmpty(message = "Email address is a required field")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotEmpty(message = "Password is a required field")
    @Size(min=5, message = "Password must have at least 5 characters")
    private String password;

    private int active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Digits(integer=2, fraction=0)
    private int age;

    private String gender;

    private boolean trad = false;

    private boolean outdoorSport = false;

    private boolean outdoorBoulder = false;

    private boolean indoorSport = false;

    private boolean indoorBoulder = false;

    public User(int id, String firstName, String lastName, String email, String password, int active, Set<Role> roles, int age, String gender, boolean trad, boolean outdoorSport, boolean outdoorBoulder, boolean indoorSport, boolean indoorBoulder) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.age = age;
        this.gender = gender;
        this.trad = trad;
        this.outdoorSport = outdoorSport;
        this.outdoorBoulder = outdoorBoulder;
        this.indoorSport = indoorSport;
        this.indoorBoulder = indoorBoulder;
    }

    public User() {}

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isTrad() {
        return trad;
    }

    public void setTrad(boolean trad) {
        this.trad = trad;
    }

    public boolean isOutdoorSport() {
        return outdoorSport;
    }

    public void setOutdoorSport(boolean outdoorSport) {
        this.outdoorSport = outdoorSport;
    }

    public boolean isOutdoorBoulder() {
        return outdoorBoulder;
    }

    public void setOutdoorBoulder(boolean outdoorBoulder) {
        this.outdoorBoulder = outdoorBoulder;
    }

    public boolean isIndoorSport() {
        return indoorSport;
    }

    public void setIndoorSport(boolean indoorSport) {
        this.indoorSport = indoorSport;
    }

    public boolean isIndoorBoulder() {
        return indoorBoulder;
    }

    public void setIndoorBoulder(boolean indoorBoulder) {
        this.indoorBoulder = indoorBoulder;
    }
}
