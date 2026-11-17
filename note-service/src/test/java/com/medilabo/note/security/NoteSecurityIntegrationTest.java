package com.medilabo.note.security;

import com.medilabo.note.model.Note;
import com.medilabo.note.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NoteSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    public void cleanDatabase() {
        noteRepository.deleteAll();
    }

    @Test
    public void shouldRejectUnauthenticatedRequest() throws Exception {

        mockMvc.perform(get("/notes/patient/2"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldAllowAuthenticatedRequestAndReturnPatientNotes() throws Exception {

        Note note = new Note();
        note.setPatientId(2);
        note.setPatient("TestBorderline");
        note.setNote("Note de test d'intégration");

        noteRepository.save(note);

        mockMvc.perform(get("/notes/patient/2")
                        .with(httpBasic("test-user", "test-password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].patientId").value(2))
                .andExpect(jsonPath("$[0].patient").value("TestBorderline"))
                .andExpect(jsonPath("$[0].note")
                        .value("Note de test d'intégration"));
    }
}
