package com.medilabo.frontend.service;

import com.medilabo.frontend.client.NoteClient;
import com.medilabo.frontend.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteClient noteClient;

    public NoteService(NoteClient noteClient) {
        this.noteClient = noteClient;
    }

    public List<Note> getNotesByPatientId(Integer patientId) {
        return noteClient.getNotesByPatientId(patientId);
    }

    public Note createNote(Note note) {
        return noteClient.createNote(note);
    }
}
