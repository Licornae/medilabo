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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
