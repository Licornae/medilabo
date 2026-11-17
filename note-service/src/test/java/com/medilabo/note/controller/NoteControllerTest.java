package com.medilabo.note.controller;

import com.medilabo.note.model.Note;
import com.medilabo.note.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

    @Test
    public void shouldCreatNote() throws Exception {

        Note noteToCreate = new Note();
        noteToCreate.setPatientId(2);
        noteToCreate.setPatient("TestBorderline");
        noteToCreate.setNote("Le patient déclare ressentir beaucoup de stress au travail");

        Note savedNote = new Note();
        savedNote.setId("1");
        savedNote.setPatientId(2);
        savedNote.setPatient("TestBorderline");
        savedNote.setNote("Le patient déclare ressentir beaucoup de stress au travail");

        when(noteService.createNote(any(Note.class))).thenReturn(savedNote);

        mockMvc.perform(post("/notes")
                .contentType("application/json")
                .content("""
                            {
                              "patientId": 2,
                              "patient": "TestBorderline",
                              "note": "Le patient déclare ressentir beaucoup de stress au travail"
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patientId").value(2))
                .andExpect(jsonPath("$.patient").value("TestBorderline"))
                .andExpect(jsonPath("$.note").value("Le patient déclare ressentir beaucoup de stress au travail"));
    }
}
