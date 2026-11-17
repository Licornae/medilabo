package com.medilabo.note.repository;
import com.medilabo.note.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@DataMongoTest
@ActiveProfiles("test")
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    void cleanDatabase() {
        noteRepository.deleteAll();
    }

    @Test
    void shouldFindNotesByPatientId() {

        Note note1 = new Note();
        note1.setPatientId(2);
        note1.setPatient("TestBorderline");
        note1.setNote("Première note");

        Note note2 = new Note();
        note2.setPatientId(2);
        note2.setPatient("TestBorderline");
        note2.setNote("Deuxième note");

        Note note3 = new Note();
        note3.setPatientId(3);
        note3.setPatient("TestInDanger");
        note3.setNote("Autre patient");

        noteRepository.save(note1);
        noteRepository.save(note2);
        noteRepository.save(note3);

        List<Note> result = noteRepository.findByPatientId(2);

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(Note::getPatientId)
                .containsOnly(2);
    }
}