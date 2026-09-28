package com.medilabo.patient.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(Integer id) {
        super("patient not found with id: " + id);
    }
}
