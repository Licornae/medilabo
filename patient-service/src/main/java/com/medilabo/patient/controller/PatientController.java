package com.medilabo.patient.controller;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient( @Valid @RequestBody Patient patient) {

        Patient createdPatient = patientService.createPatient(patient);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdPatient);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {

        List<Patient> patients = patientService.getAllPatients();

        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Integer id) {

        Patient patient = patientService.getPatientById(id);

        return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable Integer id,
            @Valid @RequestBody Patient patient) {

        Patient updatedPatient = patientService.updatePatient(id, patient);

        return ResponseEntity.ok(updatedPatient);
    }
}
