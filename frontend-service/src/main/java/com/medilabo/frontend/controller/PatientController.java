package com.medilabo.frontend.controller;

import com.medilabo.frontend.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/patients/{id}")
    public String getPatientById(@PathVariable Integer id, Model model) {

        model.addAttribute("patient", patientService.getPatientById(id));

        return "patient-detail";
    }
}
