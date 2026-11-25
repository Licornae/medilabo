package com.medilabo.assessment.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class RiskAssessmentServiceTest {

    @Test
    public void shouldReturnNoneWhenNoTriggerIsFound() {

        RiskAssessmentService riskAssessmentService = new RiskAssessmentService();

        List<String> notes = List.of(
                "Le patient déclare qu'il se sent très bien"
        );

        String result = riskAssessmentService.assess(notes, 59, "F");

        assertThat(result).isEqualTo("None");
    }

    @Test
    public void shouldReturnBorderlineForPatientOver30WithThreeTriggers() {

        RiskAssessmentService riskAssessmentService = new RiskAssessmentService();

        List<String> notes = List.of(
                "Le patient déclare qu'il ressent beaucoup de stress au travail " +
                        "Il se plaint également que son audition est anormale dernièrement",

                "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois " +
                        "Il remarque également que son audition continue d'être anormale"
        );

        String result = riskAssessmentService.assess(notes, 40, "M");

        assertThat(result).isEqualTo("Borderline");
    }
}
