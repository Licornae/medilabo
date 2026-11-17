package com.medilabo.note.config;

import com.medilabo.note.model.Note;
import com.medilabo.note.repository.NoteRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!test")
public class NoteDataInitializer {

    private final NoteRepository noteRepository;

    public NoteDataInitializer(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void run() {

        saveIfMissing(
                1,
                "TestNone",
                "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé"
        );

        saveIfMissing(
                2,
                "TestBorderline",
                "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"
        );

        saveIfMissing(
                2,
                "TestBorderline",
                "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"
        );

        saveIfMissing(
                3,
                "TestInDanger",
                "Le patient déclare qu'il fume depuis peu"
        );

        saveIfMissing(
                3,
                "TestInDanger",
                "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"
        );

        saveIfMissing(
                4,
                "TestEarlyOnset",
                "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"
        );

        saveIfMissing(
                4,
                "TestEarlyOnset",
                "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"
        );

        saveIfMissing(
                4,
                "TestEarlyOnset",
                "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"
        );

        saveIfMissing(
                4,
                "TestEarlyOnset",
                "Taille, Poids, Cholestérol, Vertige et Réaction"
        );
    }

    private void saveIfMissing(
            Integer patientId,
            String patient,
            String noteText) {

        List<Note> existingNotes =
                noteRepository.findByPatientId(patientId);

        boolean alreadyExists = existingNotes.stream()
                .anyMatch(existingNote -> noteText.equals(existingNote.getNote()));

        if (!alreadyExists) {
            Note note = new Note();
            note.setPatientId(patientId);
            note.setPatient(patient);
            note.setNote(noteText);

            noteRepository.save(note);
        }
    }
}