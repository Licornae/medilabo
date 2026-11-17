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

        when(noteRepository.findByPatientId(anyInt())).thenReturn(List.of());

        NoteDataInitializer initializer = new NoteDataInitializer(noteRepository);

        initializer.run();

        verify(noteRepository, times(9)).save(any(Note.class));
    }

    @Test
    public void shouldNotCreateDuplicatesWhenNotesAlreadyExist() throws Exception {

        NoteRepository noteRepository = mock(NoteRepository.class);

        Note note1 = new Note();
        note1.setPatientId(1);
        note1.setPatient("TestNone");
        note1.setNote(
                "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé"
        );

        Note note2a = new Note();
        note2a.setPatientId(2);
        note2a.setPatient("TestBorderline");
        note2a.setNote(
                "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"
        );

        Note note2b = new Note();
        note2b.setPatientId(2);
        note2b.setPatient("TestBorderline");
        note2b.setNote(
                "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"
        );

        Note note3a = new Note();
        note3a.setPatientId(3);
        note3a.setPatient("TestInDanger");
        note3a.setNote(
                "Le patient déclare qu'il fume depuis peu"
        );

        Note note3b = new Note();
        note3b.setPatientId(3);
        note3b.setPatient("TestInDanger");
        note3b.setNote(
                "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"
        );

        Note note4a = new Note();
        note4a.setPatientId(4);
        note4a.setPatient("TestEarlyOnset");
        note4a.setNote(
                "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"
        );

        Note note4b = new Note();
        note4b.setPatientId(4);
        note4b.setPatient("TestEarlyOnset");
        note4b.setNote(
                "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"
        );

        Note note4c = new Note();
        note4c.setPatientId(4);
        note4c.setPatient("TestEarlyOnset");
        note4c.setNote(
                "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"
        );

        Note note4d = new Note();
        note4d.setPatientId(4);
        note4d.setPatient("TestEarlyOnset");
        note4d.setNote(
                "Taille, Poids, Cholestérol, Vertige et Réaction"
        );

        when(noteRepository.findByPatientId(1))
                .thenReturn(List.of(note1));

        when(noteRepository.findByPatientId(2))
                .thenReturn(List.of(note2a, note2b));

        when(noteRepository.findByPatientId(3))
                .thenReturn(List.of(note3a, note3b));

        when(noteRepository.findByPatientId(4))
                .thenReturn(List.of(note4a, note4b, note4c, note4d));

        NoteDataInitializer initializer =
                new NoteDataInitializer(noteRepository);

        initializer.run();

        verify(noteRepository, never())
                .save(any(Note.class));
    }
}
