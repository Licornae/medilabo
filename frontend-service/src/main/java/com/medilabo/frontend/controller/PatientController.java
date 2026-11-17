package com.medilabo.frontend.controller;

import com.medilabo.frontend.model.Note;
import com.medilabo.frontend.model.Patient;
import com.medilabo.frontend.service.NoteService;
import com.medilabo.frontend.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PatientController {

    private final NoteService noteService;
    private final PatientService patientService;

    public PatientController(NoteService noteService, PatientService patientService) {
        this.noteService = noteService;
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public String getPatients(Model model) {

        model.addAttribute("patients", patientService.getAllPatients());

        return "patients";
    }

    @GetMapping("/patients/{id}")
    public String getPatientById(@PathVariable Integer id, Model model) {

        model.addAttribute("patient", patientService.getPatientById(id));
        model.addAttribute("notes", noteService.getNotesByPatientId(id));

        return "patient-detail";
    }

    @GetMapping("/patients/{id}/edit")
    public String showEditForm(
            @PathVariable Integer id,
            Model model) {

        model.addAttribute("patient", patientService.getPatientById(id));

        return "patient-edit";
    }

    @PostMapping("/patients/{id}/edit")
    public String updatePatient(
            @PathVariable Integer id,
            @ModelAttribute Patient patient) {

        patientService.updatePatient(id, patient);

        return "redirect:/patients/" + id;
    }

    @GetMapping("/patients/new")
    public String showCreateForm(Model model) {

        model.addAttribute("patient", new Patient());

        return "patient-create";
    }

    @PostMapping("/patients/new")
    public String createPatient(@ModelAttribute Patient patient) {

        Patient createdPatient = patientService.createPatient(patient);

        return "redirect:/patients/" + createdPatient.getId();
    }

    @PostMapping("/patients/{id}/notes")
    public String createNoteForPatient(@PathVariable Integer id, @ModelAttribute Note note) {
        note.setPatientId(id);
        noteService.createNote(note);
        return "redirect:/patients/" + id;
    }
}
