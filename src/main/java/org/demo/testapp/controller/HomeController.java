package org.demo.testapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/")
public class HomeController {

    @GetMapping("/a")
    public String listPatients() {
        return "list";
    }

    @GetMapping("/patient")
    public String getPatient() {
        return "show";
    }

}
