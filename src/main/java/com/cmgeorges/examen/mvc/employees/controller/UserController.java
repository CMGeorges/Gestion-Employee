package com.cmgeorges.examen.mvc.employees.controller;

import com.cmgeorges.examen.mvc.employees.controller.dto.UserRegistrationDto;
import com.cmgeorges.examen.mvc.employees.model.User;
import com.cmgeorges.examen.mvc.employees.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/registration")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    //on peut prendre le model d'ici
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String displayRegistration() {
        return "registration";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        User user = userService.GetUserByEmail(registrationDto);

        if (user != null)
            return "redirect:/registration?error";
        else {
            userService.register(registrationDto);
            return "redirect:/employee/";
        }


    }
}
