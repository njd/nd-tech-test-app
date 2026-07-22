package org.demo.testapp.controller;

import org.demo.testapp.model.Patient;
import org.demo.testapp.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/list")
    public String listPatients(Model model) {

        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);

        return "list";
    }
}
