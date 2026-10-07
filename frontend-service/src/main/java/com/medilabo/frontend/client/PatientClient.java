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
}
