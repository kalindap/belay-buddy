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
public class Climber {

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

    private ArrayList<String> climbingTypes = new ArrayList<>();

    public Climber(int id, String firstName, String lastName, String email, int age, ArrayList climbingTypes) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.climbingTypes = climbingTypes;

    }

    public Climber() {}

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

    public ArrayList<String> getClimbingTypes() {
        return climbingTypes;
    }

    public void setClimbingTypes(ArrayList<String> climbingTypes) {
        this.climbingTypes = climbingTypes;
    }
}
