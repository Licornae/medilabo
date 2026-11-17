package com.medilabo.note.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class NoteSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRejectUnauthenticatedRequest() throws Exception {

        mockMvc.perform(get("/notes/patient/2"))
                .andExpect(status().isUnauthorized());
    }
}