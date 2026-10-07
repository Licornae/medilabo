package com.medilabo.frontend.service;

import com.medilabo.frontend.client.PatientClient;
import com.medilabo.frontend.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientClient patientClient;

    public PatientService(PatientClient patientClient) {
        this.patientClient = patientClient;
    }

    public List<Patient> getAllPatients() {
        return patientClient.getAllPatients();
    }

    public Patient getPatientById(Integer id) {return patientClient.getPatientById(id);}

    public Patient updatePatient(Integer id, Patient patient) {return patientClient.updatePatient(id, patient);}

    public Patient createPatient(Patient patient) {return patientClient.createPatient(patient);}
}
