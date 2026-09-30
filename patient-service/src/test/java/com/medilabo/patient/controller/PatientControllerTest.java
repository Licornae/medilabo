package com.medilabo.patient.controller;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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


}
