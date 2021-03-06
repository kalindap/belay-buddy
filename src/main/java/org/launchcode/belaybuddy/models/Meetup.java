package org.launchcode.belaybuddy.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kalindapiper on 8/16/17.
 */
@Entity
public class Meetup {

    @Id
    @GeneratedValue
    @Column(name = "meetup_id")
    private Long id;

    @NotEmpty
    private String location;

    private LocalDate date;

    private LocalTime time;

    private String ampm;

    @ManyToOne
    private User organizer;

    @ManyToMany
    @JoinTable(name = "user_meetups_attending", joinColumns = @JoinColumn(name = "meetup_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> attendees = new ArrayList<>();

    private String description;

    public Meetup(String location, String ampm, String description) {
        this.location = location;
        this.ampm = ampm;
        this.description = description;
    }

    public Meetup() {}

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getAmpm() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<User> attendees) {
        this.attendees = attendees;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
