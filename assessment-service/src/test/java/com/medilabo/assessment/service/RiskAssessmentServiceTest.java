package com.medilabo.assessment.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class RiskAssessmentServiceTest {

    @Test
    void shouldReturnNoneWhenNoTriggerIsFound() {

        RiskAssessmentService riskAssessmentService = new RiskAssessmentService();

        List<String> notes = List.of(
                "Le patient déclare qu'il se sent très bien"
        );

        String result = riskAssessmentService.assess(notes, 59, "F");

        assertThat(result).isEqualTo("None");
    }
}
