package org.launchcode.belaybuddy.controllers;

import org.launchcode.belaybuddy.data.MeetupRepository;
import org.launchcode.belaybuddy.data.UserRepository;
import org.launchcode.belaybuddy.models.Meetup;
import org.launchcode.belaybuddy.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Created by kalindapiper on 8/16/17.
 */
@Controller
@RequestMapping(value = "calendar")
public class MeetupController {

    @Autowired
    private MeetupRepository meetupRepository;

    @Autowired
    private UserRepository userRepository;

    //displays list of all meetups
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {

        //get list of all meetups
        List<Meetup> allMeetups = meetupRepository.findAll();

        //remove meetups in the past
        allMeetups.removeIf((Meetup meetup) -> meetup.getDate().isBefore(LocalDate.now()));

        //sort meetups chronologically
        allMeetups.sort(Comparator.comparing(Meetup::getDate).thenComparing(Meetup::getAmpm).thenComparing(Meetup::getTime));

        model.addAttribute("title", "Calendar");
        model.addAttribute("meetups", allMeetups);
        return "/calendar/meetups";
    }

    //displays form for creating meetups
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("title", "Create a Meetup");
        model.addAttribute(new Meetup());
        return "calendar/create";
    }

    //process meetup creation
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String processCreateForm(@ModelAttribute @Valid Meetup newMeetup, Errors errors, @RequestParam String date,
                                    @RequestParam String hour, @RequestParam String minute, Model model) {

        //check date entered in correct format
        LocalDate dateToStore;
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            dateToStore = LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException ex) {
            model.addAttribute("title", "Create a Meetup");
            model.addAttribute("dateError", "Date must be in the format MM/DD/YYYY");
            return "/calendar/create";
        }

        //check that date has not already passed
        if (dateToStore.isBefore(LocalDate.now())) {
            model.addAttribute("title", "Create a Meetup");
            model.addAttribute("dateError", "New meetups must have a future date");
            return "/calendar/create";
        }

        //check time entered in correct format
        LocalTime timeToStore;
        String stringTime = hour + ":" + minute;
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm");
            timeToStore = LocalTime.parse(stringTime, timeFormatter);
        } catch (DateTimeParseException ex) {
            model.addAttribute("title", "Create a Meetup");
            model.addAttribute("timeError", "Time must be in the format HH:MM");
            return "/calendar/create";
        }

        //get currently logged in user to set as organizer
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User organizer = userRepository.findByEmail(username);

        newMeetup.setOrganizer(organizer);
        newMeetup.setDate(dateToStore);
        newMeetup.setTime(timeToStore);
        meetupRepository.save(newMeetup);
        return  "redirect:";
    }

    //displays form for filtering meetups
    @RequestMapping(value = "filter", method = RequestMethod.GET)
    public String filterMeetupForm(Model model) {
        model.addAttribute("title", "Filter Meetups");
        return "/calendar/filter";
    }

    //displays filtered meetup results
    @RequestMapping(value = "filter", method = RequestMethod.POST)
    public String filterMeetupResults(Model model, @RequestParam String location, @RequestParam String startDate, @RequestParam String endDate) {

        //get all meetups
        List<Meetup> allMeetups = meetupRepository.findAll();
        ArrayList<Meetup> filteredMeetups = new ArrayList<>();
        filteredMeetups.addAll(allMeetups);

        //filter meetups by location
        if (!location.equals("No Preference")) {
            filteredMeetups.removeIf((Meetup meetup) -> !meetup.getLocation().equals(location));
        }

        //filter meetups by start date
        LocalDate startDateToFilter;
        if (!startDate.equals("")) {
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                startDateToFilter = LocalDate.parse(startDate, dateFormatter);
            } catch (DateTimeParseException ex) {
                model.addAttribute("title", "Filter Meetups");
                model.addAttribute("startError", "Date must be in the format MM/DD/YYYY");
                return "/calendar/filter";
            }
        } else {
            startDateToFilter = LocalDate.MIN;
        }
        filteredMeetups.removeIf((Meetup meetup) -> meetup.getDate().isBefore(startDateToFilter));

        //filter meetups by end date
        LocalDate endDateToFilter;
        if (!endDate.equals("")) {
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                endDateToFilter = LocalDate.parse(endDate, dateFormatter);
            } catch (DateTimeParseException ex) {
                model.addAttribute("title", "Filter Meetups");
                model.addAttribute("endError", "Date must be in the format MM/DD/YYYY");
                return "/calendar/filter";
            }
        } else {
            endDateToFilter = LocalDate.MAX;
        }
        filteredMeetups.removeIf((Meetup meetup) -> meetup.getDate().isAfter(endDateToFilter));

        //display filtered meetups in chronological order
        filteredMeetups.sort(Comparator.comparing(Meetup::getDate).thenComparing(Meetup::getAmpm).thenComparing(Meetup::getTime));

        model.addAttribute("title", "Filter Results");
        model.addAttribute("meetups", filteredMeetups);
        return "/calendar/meetups";
    }

    //allows user to sign up for a meetup
    @RequestMapping(value = "addme/{meetupId}", method = RequestMethod.GET)
    public String addAttendee(Model model, @PathVariable Long meetupId) {

        //get currently logged in user to set as attendee
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User attendee = userRepository.findByEmail(username);

        //find meetup user wants to sign up for
        Meetup meetup = meetupRepository.findOne(meetupId);

        //find organizer for that meetup
        User organizer = meetup.getOrganizer();

        //find list of current attendees for that meetup
        List<User> currentAttendees = meetup.getAttendees();

        //if user is the organizer or already attending, reload page
        if (attendee.equals(organizer) || currentAttendees.contains(attendee)) {
            return "redirect:/calendar";
        }

        //else add user to meetup
        currentAttendees.add(attendee);
        meetup.setAttendees(currentAttendees);
        meetupRepository.save(meetup);
        return "redirect:/calendar";
    }

    //display user's meetups
    @RequestMapping(value="mymeetups", method = RequestMethod.GET)
    public String displayUsersMeetups(Model model) {

        //get currently logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User currentUser = userRepository.findByEmail(username);

        //get list of all meetups organized by user, remove meetups that already occurred, then sort by date and time
        List<Meetup> organized = currentUser.getMeetupsOrganized();
        organized.removeIf((Meetup meetup) -> meetup.getDate().isBefore(LocalDate.now()));
        organized.sort(Comparator.comparing(Meetup::getDate).thenComparing(Meetup::getAmpm).thenComparing(Meetup::getTime));

        //get list of all meetups attended by user, remove meetups that already occurred, then sort by date and time
        List<Meetup> attending = currentUser.getMeetupsAttending();
        attending.removeIf((Meetup meetup) -> meetup.getDate().isBefore(LocalDate.now()));
        attending.sort(Comparator.comparing(Meetup::getDate).thenComparing(Meetup::getAmpm).thenComparing(Meetup::getTime));

        model.addAttribute("title", "My Meetups");
        model.addAttribute("meetupsOrganized", organized);
        model.addAttribute("meetupsAttending", attending);
        return "/calendar/mymeetups";
    }
}
