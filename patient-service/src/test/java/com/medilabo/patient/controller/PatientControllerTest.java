package com.medilabo.patient.controller;

import com.medilabo.patient.exception.PatientNotFoundException;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WithMockUser
@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Test
    public void shouldCreatePatientWithoutOptionalFields() throws Exception {

        Patient savedPatient = new Patient();
        savedPatient.setId(1);
        savedPatient.setFirstName("Test");
        savedPatient.setLastName("TestNone");

        when(patientService.createPatient(any(Patient.class))).thenReturn(savedPatient);

        mockMvc.perform(post("/patients")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "firstName": "Test",
                              "lastName": "TestNone",
                              "birthDate": "1966-12-31",
                              "gender": "F"
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("TestNone"));
    }

    @Test
    public void shouldReturnBadRequestWhenFirstNameIsMissing() throws Exception {

        mockMvc.perform(post("/patients")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "lastName": "TestNone",
                              "birthDate": "1966-12-31",
                              "gender": "F"
                            }
                            """))
                .andExpect(status().isBadRequest());

        verify(patientService, never()).createPatient(any(Patient.class));
    }

    @Test
    public void shouldGetAllPatients() throws Exception {

        Patient patient1 = new Patient();
        patient1.setId(1);
        patient1.setFirstName("Test");
        patient1.setLastName("TestNone");

        Patient patient2 = new Patient();
        patient2.setId(2);
        patient2.setFirstName("Test");
        patient2.setLastName("TestBorderline");

        when(patientService.getAllPatients())
                .thenReturn(List.of(patient1, patient2));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].lastName").value("TestNone"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].lastName").value("TestBorderline"));

        verify(patientService).getAllPatients();
    }

    @Test
    public void shouldGetPatientById() throws Exception {

        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("Test");
        patient.setLastName("TestNone");

        when(patientService.getPatientById(1)).thenReturn(patient);

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("TestNone"));

        verify(patientService).getPatientById(1);
    }

    @Test
    public void shouldReturnNotFoundWhenPatientDoesNotExist() throws Exception {

        when(patientService.getPatientById(99)).thenThrow(new PatientNotFoundException(99));

        mockMvc.perform(get("/patients/99")).andExpect(status().isNotFound());

        verify(patientService).getPatientById(99);
    }

    @Test
    public void shouldUpdatePatient() throws Exception {

        Patient updatedPatient = new Patient();
        updatedPatient.setId(1);
        updatedPatient.setFirstName("Updated");
        updatedPatient.setLastName("Patient");
        updatedPatient.setBirthDate(LocalDate.of(1966, 12, 31));
        updatedPatient.setGender("F");

        when(patientService.updatePatient(eq(1), any(Patient.class)))
                .thenReturn(updatedPatient);

        mockMvc.perform(put("/patients/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "firstName": "Updated",
                              "lastName": "Patient",
                              "birthDate": "1966-12-31",
                              "gender": "F"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("Patient"));

        verify(patientService).updatePatient(eq(1), any(Patient.class));
    }

    @Test
    public void shouldReturnNotFoundWhenUpdatingUnknownPatient() throws Exception {

        when(patientService.updatePatient(eq(99), any(Patient.class)))
                .thenThrow(new PatientNotFoundException(99));

        mockMvc.perform(put("/patients/99")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "firstName": "Updated",
                              "lastName": "Patient",
                              "birthDate": "1966-12-31",
                              "gender": "F"
                            }
                            """))
                .andExpect(status().isNotFound());

        verify(patientService).updatePatient(eq(99), any(Patient.class));
    }
}
