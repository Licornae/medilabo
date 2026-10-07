package com.medilabo.patient.config;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Profile("!test")
public class PatientDataInitializer implements CommandLineRunner {

    private final PatientRepository patientRepository;

    public PatientDataInitializer(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void run(String... args) {

        List<Patient> patients = List.of(
                createPatient(
                        "Test",
                        "TestNone",
                        LocalDate.of(1966, 12, 31),
                        "F",
                        "1 Brookside St",
                        "100-222-3333"
                ),
                createPatient(
                        "Test",
                        "TestBorderline",
                        LocalDate.of(1945, 6, 24),
                        "M",
                        "2 High St",
                        "200-333-4444"
                ),
                createPatient(
                        "Test",
                        "TestInDanger",
                        LocalDate.of(2004, 6, 18),
                        "M",
                        "3 Club Road",
                        "300-444-5555"
                ),
                createPatient(
                        "Test",
                        "TestEarlyOnset",
                        LocalDate.of(2002, 6, 28),
                        "F",
                        "4 Valley Dr",
                        "400-555-6666"
                )
        );

        for (Patient patient : patients) {

            boolean exists =
                    patientRepository.existsByFirstNameAndLastName(
                            patient.getFirstName(),
                            patient.getLastName()
                    );

            if (!exists) {
                patientRepository.save(patient);
            }
        }
    }

    private Patient createPatient(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String gender,
            String address,
            String phoneNumber) {

        Patient patient = new Patient();

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setBirthDate(birthDate);
        patient.setGender(gender);
        patient.setAddress(address);
        patient.setPhoneNumber(phoneNumber);

        return patient;
    }
}
