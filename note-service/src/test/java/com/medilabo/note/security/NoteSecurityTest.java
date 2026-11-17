package com.medilabo.note.security;

import com.medilabo.note.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ActiveProfiles("test")
class NoteSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @Test
    public void shouldRejectUnauthenticatedRequest() throws Exception {

        mockMvc.perform(get("/notes/patient/2"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldAllowAuthenticatedRequest() throws Exception {

        when(noteService.getNotesByPatientId(2)).thenReturn(List.of());

        mockMvc.perform(get("/notes/patient/2")
                        .with(httpBasic("test-user", "test-password")))
                .andExpect(status().isOk());
    }
}