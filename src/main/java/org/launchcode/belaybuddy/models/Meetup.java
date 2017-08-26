package org.launchcode.belaybuddy.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

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

    private String description;

    public Meetup(String ampm, String location, String description) {
        this.ampm = ampm;
        this.location = location;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
