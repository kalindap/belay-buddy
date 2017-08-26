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
import org.springframework.web.bind.annotation.RequestParam;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


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
        return "/user/index";
    }

    //register form
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
        return "/user/register";
    }

    //process registration
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
            return "/user/register";
        }

        userService.saveUser(newUser);
        return "redirect:climbers";
    }

    //displays list of all users
    @RequestMapping(value = "climbers", method = RequestMethod.GET)
    public String displayClimbers(Model model) {
        model.addAttribute("title", "Climbers");
        model.addAttribute("users", userRepository.findAll());
        return "/user/climbers";
    }

    //displays form for filtering users
    @RequestMapping(value = "climbers/filter", method = RequestMethod.GET)
    public String displayFilterClimbersForm(Model model) {
        model.addAttribute("title", "Filter Climbers");
        return "/user/filter";
    }

    //displays filtered list of users
    @RequestMapping(value = "climbers/filter", method = RequestMethod.POST)
    public String displayFilterClimberResults(Model model, @RequestParam String minAge, @RequestParam String maxAge, @RequestParam String gender, @RequestParam List climbingTypes) {

        Integer intMinAge;
        Integer intMaxAge;
        List<User> allUsers = userRepository.findAll();
        ArrayList<User> filteredUsers = new ArrayList<>();


        //filter users by climbing type to create user list
        for (User user : allUsers) {
            if (climbingTypes.contains("trad")) {
                if (user.isTrad()) {
                    if (!filteredUsers.contains(user)) {
                        filteredUsers.add(user);
                    }
                }
            }
            if (climbingTypes.contains("outdoorSport")) {
                if (user.isOutdoorSport()) {
                    if (!filteredUsers.contains(user)) {
                        filteredUsers.add(user);
                    }
                }
            }
            if (climbingTypes.contains("outdoorBoulder")) {
                if (user.isOutdoorBoulder()) {
                    if (!filteredUsers.contains(user)) {
                        filteredUsers.add(user);
                    }
                }
            }
            if (climbingTypes.contains("indoorSport")) {
                if (user.isIndoorSport()) {
                    if (!filteredUsers.contains(user)) {
                        filteredUsers.add(user);
                    }
                }
            }
            if (climbingTypes.contains("indoorBoulder")) {
                if (user.isIndoorBoulder()) {
                    if (!filteredUsers.contains(user)) {
                        filteredUsers.add(user);
                    }
                }
            }
        }

        //if no climbing types entered, put all users in list
        if (climbingTypes.isEmpty()) {
            filteredUsers.addAll(allUsers);
        }


        //filter users by age, remove if they don't match criteria
        if (!minAge.equals("")) {
            try {
                intMinAge = Integer.parseInt(minAge);
            } catch (NumberFormatException ex) {
                model.addAttribute("title", "Filter Climbers");
                model.addAttribute("error", "Age must be entered as digits");
                return "/user/filter";
            }
        } else {
            intMinAge = 18;
        }
        filteredUsers.removeIf((User user) -> user.getAge() < intMinAge);

        if (!maxAge.equals("")) {
            try {
                intMaxAge = Integer.parseInt(maxAge);
            } catch (NumberFormatException ex) {
                model.addAttribute("title", "Filter Climbers");
                model.addAttribute("error", "Age must be entered as digits");
                return "/user/filter";
            }
        } else {
            intMaxAge = 80;
        }
        filteredUsers.removeIf((User user) -> user.getAge() > intMaxAge);

        //filter users by gender, remove if they don't match criteria
        if (!gender.equals("No Preference")) {
            filteredUsers.removeIf((User user) -> !user.getGender().equals(gender));
        }

        model.addAttribute("title", "Filter Results");
        model.addAttribute("users", filteredUsers);
        return "/user/climbers";
    }
}
