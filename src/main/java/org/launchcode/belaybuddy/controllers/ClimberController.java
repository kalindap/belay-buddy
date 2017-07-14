package org.launchcode.belaybuddy.controllers;

import org.launchcode.belaybuddy.models.Climber;
import org.launchcode.belaybuddy.models.data.ClimberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

/**
 * Created by kalindapiper on 7/12/17.
 */
@Controller
public class ClimberController {

    @Autowired
    private ClimberDao climberDao;

    public ClimberController() {}

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String displayRegisterForm(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute(new Climber());
        return "/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegisterForm(@ModelAttribute @Valid Climber newClimber,
                                       Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "/register";
        }

        climberDao.save(newClimber);
        return "redirect:search";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String processSearchForm(Model model) {
        model.addAttribute("title", "Search");
        model.addAttribute("climbers", climberDao.findAll());
        return "/search";
    }
}
