package com.medilabo.note.config;

import com.medilabo.note.model.Note;
import com.medilabo.note.repository.NoteRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class NoteDataInitializerTest {

    @Test
    public void shouldInitializeTheNineProvidedNotes() throws Exception {

        NoteRepository noteRepository = mock(NoteRepository.class);

        when(noteRepository.findByPatientId(anyInt()))
                .thenReturn(List.of());

        NoteDataInitializer initializer = new NoteDataInitializer(noteRepository);

        initializer.run();

        verify(noteRepository, times(9))
                .save(any(Note.class));
    }
}
