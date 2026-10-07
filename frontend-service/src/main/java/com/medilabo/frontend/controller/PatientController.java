package com.medilabo.frontend.controller;

import com.medilabo.frontend.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public String getPatients(Model model) {

        model.addAttribute("patients", patientService.getAllPatients());

        return "patients";
    }
}
