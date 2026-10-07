package com.medilabo.frontend.service;

import com.medilabo.frontend.client.PatientClient;
import com.medilabo.frontend.model.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class PatientServiceTest {
    @Test
    void shouldGetAllPatients() {

        PatientClient patientClient = mock(PatientClient.class);
        PatientService patientService = new PatientService(patientClient);

        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("Test");
        patient.setLastName("TestNone");

        when(patientClient.getAllPatients())
                .thenReturn(List.of(patient));

        List<Patient> patients = patientService.getAllPatients();

        assertThat(patients).hasSize(1);
        assertThat(patients.getFirst().getFirstName()).isEqualTo("Test");
    }
}
