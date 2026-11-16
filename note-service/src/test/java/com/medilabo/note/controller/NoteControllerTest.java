package com.medilabo.note.controller;

import com.medilabo.note.model.Note;
import com.medilabo.note.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class NoteControllerTest {

    private MockMvc mockMvc;
    private NoteService noteService;

    @BeforeEach
    public void setUp() {
        noteService = mock(NoteService.class);

        NoteController noteController = new NoteController(noteService);

        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
    }

    @Test
    public void shouldGetNotesByPatientId() throws Exception {

        Note note = new Note();
        note.setId("1");
        note.setPatientId(2);
        note.setPatient("TestBorderline");
        note.setNote("Le patient déclare ressentir beaucoup de stress au travail");

        when(noteService.getNotesByPatientId(2)).thenReturn(List.of(note));

        mockMvc.perform(get("/notes/patient/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patientId").value(2))
                .andExpect(jsonPath("$[0].patient").value("TestBorderline"))
                .andExpect(jsonPath("$[0].note").value("Le patient déclare ressentir beaucoup de stress au travail"));

    }
}
