package com.cmgeorges.examen.mvc.employees.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping("/login")
    public String login() {


        return "login";
    }

    @GetMapping("/")
    public String displayHome() {
        return "redirect:/employee/";
    }
}
