package com.medilabo.note.controller;

import com.medilabo.note.model.Note;
import com.medilabo.note.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable Integer patientId) {
        return ResponseEntity.ok(noteService.getNotesByPatientId(patientId));
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note createdNote = noteService.createNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }
}
