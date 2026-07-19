package org.demo.testapp.controller;

import org.demo.testapp.dto.PatientResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @GetMapping("/list")
    public List<PatientResponseDto> listPatients() {
        return List.of();
    }
}
