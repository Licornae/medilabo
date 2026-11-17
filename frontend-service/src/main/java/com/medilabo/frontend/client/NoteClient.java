package com.medilabo.frontend.client;

import com.medilabo.frontend.model.Note;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class NoteClient {

    private final RestClient restClient;

    public NoteClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Note> getNotesByPatientId(Integer patientId) {

        Note[] notes = restClient
                .get()
                .uri("/notes/patient/{patientId}", patientId)
                .retrieve()
                .body(Note[].class);

        if (notes == null) {
            return List.of();
        }

        return Arrays.asList(notes);
    }

    public Note createNote(Note note) {

        return restClient
                .post()
                .uri("/notes")
                .body(note)
                .retrieve()
                .body(Note.class);
    }

}