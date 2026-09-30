package com.medilabo.patient.service;

import com.medilabo.patient.model.Patient;

import java.util.List;

public interface PatientService {

    Patient createPatient(Patient patient);

    List<Patient> getAllPatients();

    Patient getPatientById(Integer id);

    Patient updatePatient(Integer id, Patient updatedPatient);
}
