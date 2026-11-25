package com.medilabo.assessment.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskAssessmentService {

    public String assess(List<String> notes, int age, String gender) {
        return "None";
    }
}
