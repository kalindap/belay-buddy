package org.launchcode.belaybuddy.controllers;


import org.launchcode.belaybuddy.models.User;
import org.launchcode.belaybuddy.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.validation.Valid;
import java.util.ArrayList;


/**
 * Created by kalindapiper on 7/12/17.
 */

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    public UserController() {}

    private ArrayList<Integer> possibleAges = new ArrayList<>();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayIndex(Model model) {
        model.addAttribute("title", "Belay Buddy Login");
        return "/index";
    }


    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String displayRegisterForm(Model model) {

        if (possibleAges.isEmpty()) {
            for (int i = 18; i < 80; i++) {
                possibleAges.add(i);
            }
        }

        model.addAttribute("title", "Register");
        model.addAttribute("possibleAges", possibleAges);
        model.addAttribute(new User());
        return "/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegisterForm(@ModelAttribute @Valid User newUser,
                                       Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            model.addAttribute("possibleAges", possibleAges);
            return "/register";
        }

        userDao.save(newUser);
        return "redirect:climbers";
    }

    @RequestMapping(value = "climbers", method = RequestMethod.GET)
    public String displayClimbersForm(Model model) {
        model.addAttribute("title", "Climbers");
        model.addAttribute("users", userDao.findAll());
        return "/climbers";
    }
}
