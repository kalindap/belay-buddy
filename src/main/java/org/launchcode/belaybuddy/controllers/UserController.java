package org.launchcode.belaybuddy.controllers;


import org.launchcode.belaybuddy.data.UserRepository;
import org.launchcode.belaybuddy.models.User;
import org.launchcode.belaybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.validation.Valid;
import java.util.ArrayList;


/**
 * Created by kalindapiper on 7/12/17.
 */

@Controller
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public UserController() {}

    //to display dropdown of possible ages for user to choose at registration
    private ArrayList<Integer> possibleAges = new ArrayList<>();

    //login page
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
        User userExists = userService.findUserByEmail(newUser.getEmail());
        if (userExists != null) {
            errors.rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            model.addAttribute("possibleAges", possibleAges);
            return "/register";
        }

        userService.saveUser(newUser);
        return "redirect:climbers";
    }

    //displays list of all users
    @RequestMapping(value = "climbers", method = RequestMethod.GET)
    public String displayClimbersForm(Model model) {
        model.addAttribute("title", "Climbers");
        model.addAttribute("users", userRepository.findAll());
        return "/climbers";
    }
}
