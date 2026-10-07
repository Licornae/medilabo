package com.medilabo.frontend.client;

import com.medilabo.frontend.model.Patient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class PatientClient {

    private final RestClient restClient;

    public PatientClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Patient> getAllPatients() {

        Patient[] patients = restClient
                .get()
                .uri("/patients")
                .retrieve()
                .body(Patient[].class);

        if (patients == null) {
            return List.of();
        }

        return Arrays.asList(patients);
    }

    public Patient getPatientById(Integer id) {

        return restClient
                .get()
                .uri("/patients/{id}", id)
                .retrieve()
                .body(Patient.class);
    }

    public Patient updatePatient(Integer id, Patient patient) {

        return restClient
                .put()
                .uri("/patients/{id}", id)
                .body(patient)
                .retrieve()
                .body(Patient.class);
    }
}
