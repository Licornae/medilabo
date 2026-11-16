package com.medilabo.note.repository;

import com.medilabo.note.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findByPatientId(Integer patientId);

}
