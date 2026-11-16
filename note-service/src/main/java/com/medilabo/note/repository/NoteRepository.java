package com.medilabo.note.repository;

import com.medilabo.note.model.Note;

import java.util.List;

public interface NoteRepository {

    List<Note> findByPatientId(Integer patientId);

}
