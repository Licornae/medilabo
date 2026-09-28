package com.medilabo.patient.service;


import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    public void shouldCreatePatient() {

        // Arrange
        Patient patient = new Patient();
        patient.setFirstName("Test");
        patient.setLastName("TestNone");
        patient.setBirthDate(LocalDate.of(1966, 12, 31));
        patient.setGender("F");

        Patient savedPatient = new Patient();
        savedPatient.setId(1);
        savedPatient.setFirstName("Test");
        savedPatient.setLastName("TestNone");
        savedPatient.setBirthDate(LocalDate.of(1966, 12, 31));
        savedPatient.setGender("F");

        when(patientRepository.save(patient)).thenReturn(savedPatient);

        // Act
        Patient result = patientService.createPatient(patient);

        // Assert
        assertEquals(1, result.getId());
        assertEquals("Test", result.getFirstName());
        assertEquals("TestNone", result.getLastName());

        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void shouldGetAllPatients() {

        // Arrange
        Patient patient1 = new Patient();
        patient1.setId(1);
        patient1.setFirstName("Test");
        patient1.setLastName("TestNone");

        Patient patient2 = new Patient();
        patient2.setId(2);
        patient2.setFirstName("Test");
        patient2.setLastName("TestBorderline");

        List<Patient> patients = List.of(patient1, patient2);

        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<Patient> result = patientService.getAllPatients();

        // Assert
        assertEquals(2, result.size());
        assertEquals("TestNone", result.get(0).getLastName());
        assertEquals("TestBorderline", result.get(1).getLastName());

        verify(patientRepository).findAll();
    }

    @Test
    public void shouldGetPatientById() {

        // Arrange
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("Test");
        patient.setLastName("TestNone");

        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));

        // Act
        Patient result = patientService.getPatientById(1);

        // Assert
        assertEquals(1, result.getId());
        assertEquals("TestNone", result.getLastName());

        verify(patientRepository).findById(1);
    }

    @Test
    public void shouldThrowExceptionWhenPatientNotFound() {

        // Arrange
        when(patientRepository.findById(99))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(99));

        verify(patientRepository).findById(99);
    }
}
