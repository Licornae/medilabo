package com.medilabo.patient.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        patient.setDateOfBirth(LocalDate.of(1966, 12, 31));
        patient.setGender("F");

        Patient savedPatient = new Patient();
        savedPatient.setId(1);
        savedPatient.setFirstName("Test");
        savedPatient.setLastName("TestNone");
        savedPatient.setDateOfBirth(LocalDate.of(1966, 12, 31));
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
}
