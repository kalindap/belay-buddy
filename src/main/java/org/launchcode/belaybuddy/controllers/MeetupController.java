package org.launchcode.belaybuddy.controllers;

import org.launchcode.belaybuddy.data.MeetupRepository;
import org.launchcode.belaybuddy.data.UserRepository;
import org.launchcode.belaybuddy.models.Meetup;
import org.launchcode.belaybuddy.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


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
        model.addAttribute("title", "Calendar");
        model.addAttribute("meetups", meetupRepository.findAll());
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
    public String processCreateForm(@ModelAttribute @Valid Meetup newMeetup, Errors errors, @RequestParam String date, @RequestParam String hour, @RequestParam String minute, Model model) {

        LocalDate dateToStore;
        LocalTime timeToStore;

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            dateToStore = LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException ex) {
            model.addAttribute("title", "Create a Meetup");
            model.addAttribute("dateError", "Date must be in the format MM/DD/YYYY");
            return "/calendar/create";
        }

        String stringTime = hour + ":" + minute;

        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm");
            timeToStore = LocalTime.parse(stringTime, timeFormatter);
        } catch (DateTimeParseException ex) {
            model.addAttribute("title", "Create a Meetup");
            model.addAttribute("timeError", "Time must be in the format H:MM");
            return "/calendar/create";
        }


        Long organizerid = Long.valueOf(1);
        User placeholder = userRepository.getOne(organizerid);
        newMeetup.setOrganizer(placeholder);

        newMeetup.setDate(dateToStore);
        newMeetup.setTime(timeToStore);
        meetupRepository.save(newMeetup);
        return  "redirect:";
    }
}
