package com.medilabo.patient.config;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PatientDataInitializerTest {

    @Test
    public void shouldInsertExpectedPatients() {

        PatientRepository patientRepository = mock(PatientRepository.class);

        when(patientRepository.existsByFirstNameAndLastName(
                anyString(),
                anyString()))
                .thenReturn(false);

        PatientDataInitializer initializer =
                new PatientDataInitializer(patientRepository);

        initializer.run();

        ArgumentCaptor<Patient> captor =
                ArgumentCaptor.forClass(Patient.class);

        verify(patientRepository, times(4))
                .save(captor.capture());

        List<Patient> savedPatients = captor.getAllValues();

        assertThat(savedPatients)
                .extracting(
                        Patient::getFirstName,
                        Patient::getLastName
                )
                .containsExactly(
                        tuple("Test", "TestNone"),
                        tuple("Test", "TestBorderline"),
                        tuple("Test", "TestInDanger"),
                        tuple("Test", "TestEarlyOnset")
                );
    }

    @Test
    public void shouldNotInsertExistingPatients() {

        PatientRepository patientRepository = mock(PatientRepository.class);

        when(patientRepository.existsByFirstNameAndLastName(anyString(), anyString()))
                .thenReturn(true);

        PatientDataInitializer initializer =
                new PatientDataInitializer(patientRepository);

        initializer.run();

        verify(patientRepository, never()).save(any(Patient.class));
    }
}
