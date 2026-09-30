package com.medilabo.patient.service;

import com.medilabo.patient.exception.PatientNotFoundException;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Integer id) {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    @Override
    public Patient updatePatient(Integer id, Patient updatedPatient) {

        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        existingPatient.setFirstName(updatedPatient.getFirstName());
        existingPatient.setLastName(updatedPatient.getLastName());
        existingPatient.setBirthDate(updatedPatient.getBirthDate());
        existingPatient.setGender(updatedPatient.getGender());
        existingPatient.setAddress(updatedPatient.getAddress());
        existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());

        return patientRepository.save(existingPatient);
    }
}
