package org.launchcode.belaybuddy.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

/**
 * Created by kalindapiper on 7/12/17.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15, message = "Name must be between 3 and 15 characters")
    private String firstName;

    @NotNull
    @Size(min=3, max=20, message = "Name must be between 3 and 20 characters")
    private String lastName;

    @Email
    private String email;

    @Digits(integer=2, fraction=0)
    private int age;

    private String gender;

    private boolean trad = false;

    private boolean outdoorSport = false;

    private boolean outdoorBoulder = false;

    private boolean indoorSport = false;

    private boolean indoorBoulder = false;

    public User(int id, String firstName, String lastName, String email, int age, String gender, boolean trad, boolean outdoorSport, boolean outdoorBoulder, boolean indoorSport, boolean indoorBoulder) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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
