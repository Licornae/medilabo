package com.medilabo.frontend.service;

import com.medilabo.frontend.model.Note;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NoteServiceTest {

    @Test
    public void shouldGetNotesByPatientId() {

        NoteClient noteClient = mock(NoteClient.class);

        Note note = new Note();
        note.setPatientId(2);
        note.setPatient("TestBorderline");
        note.setNote("Une note médicale");

        when(noteClient.getNotesByPatientId(2)).thenReturn(List.of(note));

        NoteService noteService = new NoteService(noteClient);

        List<Note> result = noteService.getNotesByPatientId(2);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPatientId()).isEqualTo(2);
        assertThat(result.get(0).getPatient()).isEqualTo("TestBorderline");
        assertThat(result.get(0).getNote()).isEqualTo("Une note médicale");
    }
}