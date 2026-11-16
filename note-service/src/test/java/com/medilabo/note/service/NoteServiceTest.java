package com.medilabo.note.service;

import com.medilabo.note.model.Note;
import com.medilabo.note.repository.NoteRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class NoteServiceTest {

    @Test
    public void shouldGetNotesByPatientId() {

        NoteRepository noteRepository = mock(NoteRepository.class);

        Note note1 = new Note();
        note1.setPatientId(2);
        note1.setPatient("TestBorderline");
        note1.setNote("Première note");

        Note note2 = new Note();
        note2.setPatientId(2);
        note2.setPatient("TestBorderline");
        note2.setNote("Deuxième note");

        when(noteRepository.findByPatientId(2)).thenReturn(List.of(note1, note2));

        NoteService noteService = new NoteService(noteRepository);
        List<Note> result = noteService.getNotesByPatientId(2);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPatientId()).isEqualTo(2);
        assertThat(result.get(1).getPatientId()).isEqualTo(2);
        assertThat(result.get(0).getNote()).isEqualTo("Première note");
        assertThat(result.get(1).getNote()).isEqualTo("Deuxième note");

        verify(noteRepository).findByPatientId(2);
    }

    @Test
    public void shouldCreateNote() {

        NoteRepository noteRepository = mock(NoteRepository.class);

        Note note = new Note();
        note.setPatientId(2);
        note.setPatient("TestBorderline");
        note.setNote("Le patient déclare ressentir beaucoup de stress au travail");

        Note savedNote = new Note();
        savedNote.setId("1");
        savedNote.setPatientId(2);
        savedNote.setPatient("TestBorderline");
        savedNote.setNote("Le patient déclare ressentir beaucoup de stress au travail");

        when(noteRepository.save(note)).thenReturn(savedNote);

        NoteService noteService = new NoteService(noteRepository);

        Note result = noteService.createNote(note);

        assertThat(result.getId()).isEqualTo("1");
        assertThat(result.getPatientId()).isEqualTo(2);
        assertThat(result.getPatient()).isEqualTo("TestBorderline");
        assertThat(result.getNote()).isEqualTo("Le patient déclare ressentir beaucoup de stress au travail");

        verify(noteRepository).save(note);
    }
}
