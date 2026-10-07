package com.medilabo.frontend.controller;

import com.medilabo.frontend.model.Patient;
import com.medilabo.frontend.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@WithMockUser
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Test
    public void shouldDisplayPatientList() throws Exception {

        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("Test");
        patient.setLastName("TestNone");

        when(patientService.getAllPatients())
                .thenReturn(List.of(patient));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    @WithMockUser
    public void shouldDisplayPatientDetails() throws Exception {

        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("Test");
        patient.setLastName("TestNone");

        when(patientService.getPatientById(1))
                .thenReturn(patient);

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient-detail"))
                .andExpect(model().attribute("patient", patient));
    }

    @Test
    @WithMockUser
    public void shouldDisplayEditPatientForm() throws Exception {

        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("Test");
        patient.setLastName("TestNone");

        when(patientService.getPatientById(1))
                .thenReturn(patient);

        mockMvc.perform(get("/patients/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient-edit"))
                .andExpect(model().attribute("patient", patient));
    }

    @Test
    @WithMockUser
    public void shouldUpdatePatient() throws Exception {

        Patient updatedPatient = new Patient();
        updatedPatient.setId(1);
        updatedPatient.setFirstName("Updated");
        updatedPatient.setLastName("TestNone");

        when(patientService.updatePatient(eq(1), any(Patient.class)))
                .thenReturn(updatedPatient);

        mockMvc.perform(post("/patients/1/edit")
                        .with(csrf())
                        .param("firstName", "Updated")
                        .param("lastName", "TestNone")
                        .param("birthDate", "1966-12-31")
                        .param("gender", "F")
                        .param("address", "New address")
                        .param("phoneNumber", "999-999-9999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/1"));
    }

    @Test
    @WithMockUser
    public void shouldDisplayCreatePatientForm() throws Exception {

        mockMvc.perform(get("/patients/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient-create"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    @WithMockUser
    public void shouldCreatePatient() throws Exception {

        Patient createdPatient = new Patient();
        createdPatient.setId(5);
        createdPatient.setFirstName("New");
        createdPatient.setLastName("Patient");

        when(patientService.createPatient(any(Patient.class)))
                .thenReturn(createdPatient);

        mockMvc.perform(post("/patients/new")
                        .with(csrf())
                        .param("firstName", "New")
                        .param("lastName", "Patient")
                        .param("birthDate", "1990-01-01")
                        .param("gender", "M")
                        .param("address", "")
                        .param("phoneNumber", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/5"));
    }
}
