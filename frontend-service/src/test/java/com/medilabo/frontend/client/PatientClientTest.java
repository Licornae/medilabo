package com.medilabo.frontend.client;

import com.medilabo.frontend.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.assertj.core.api.Assertions.assertThat;

public class PatientClientTest {

    private MockRestServiceServer server;
    private PatientClient patientClient;

    @BeforeEach
    public void setUp() {
        RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8081");

        server = MockRestServiceServer.bindTo(builder).build();

        patientClient = new PatientClient(builder.build());
    }

    @Test
    public void shouldGetAllPatients() {

        String json = """
                [
                    {
                        "id": 1,
                        "firstName": "Test",
                        "lastName": "TestNone",
                        "birthDate": "1966-12-31",
                        "gender": "F",
                        "address": "1 Brookside St",
                        "phoneNumber": "100-222-3333"
                    }
                ]
                """;

        server.expect(requestTo("http://localhost:8081/patients"))
                .andExpect(method(GET))
                .andRespond(withSuccess(
                        json,
                        org.springframework.http.MediaType.APPLICATION_JSON
                ));

        List<Patient> patients = patientClient.getAllPatients();

        assertThat(patients).hasSize(1);
        assertThat(patients.getFirst().getFirstName()).isEqualTo("Test");
        assertThat(patients.getFirst().getLastName()).isEqualTo("TestNone");

        server.verify();
    }

    @Test
    public void shouldGetPatientById() {

        String json = """
            {
                "id": 1,
                "firstName": "Test",
                "lastName": "TestNone",
                "birthDate": "1966-12-31",
                "gender": "F",
                "address": "1 Brookside St",
                "phoneNumber": "100-222-3333"
            }
            """;

        server.expect(requestTo("http://localhost:8081/patients/1"))
                .andExpect(method(GET))
                .andRespond(withSuccess(
                        json,
                        org.springframework.http.MediaType.APPLICATION_JSON
                ));

        Patient patient = patientClient.getPatientById(1);

        assertThat(patient.getId()).isEqualTo(1);
        assertThat(patient.getFirstName()).isEqualTo("Test");
        assertThat(patient.getLastName()).isEqualTo("TestNone");

        server.verify();
    }

    @Test
    public void shouldUpdatePatient() {

        Patient patientToUpdate = new Patient();
        patientToUpdate.setId(1);
        patientToUpdate.setFirstName("Updated");
        patientToUpdate.setLastName("TestNone");
        patientToUpdate.setBirthDate(java.time.LocalDate.of(1966, 12, 31));
        patientToUpdate.setGender("F");
        patientToUpdate.setAddress("New address");
        patientToUpdate.setPhoneNumber("999-999-9999");

        String json = """
            {
                "id": 1,
                "firstName": "Updated",
                "lastName": "TestNone",
                "birthDate": "1966-12-31",
                "gender": "F",
                "address": "New address",
                "phoneNumber": "999-999-9999"
            }
            """;

        server.expect(requestTo("http://localhost:8081/patients/1"))
                .andExpect(method(org.springframework.http.HttpMethod.PUT))
                .andRespond(withSuccess(
                        json,
                        org.springframework.http.MediaType.APPLICATION_JSON
                ));

        Patient updatedPatient = patientClient.updatePatient(1, patientToUpdate);

        assertThat(updatedPatient.getId()).isEqualTo(1);
        assertThat(updatedPatient.getFirstName()).isEqualTo("Updated");
        assertThat(updatedPatient.getAddress()).isEqualTo("New address");

        server.verify();
    }

    @Test
    public void shouldCreatePatient() {

        Patient patientToCreate = new Patient();
        patientToCreate.setFirstName("New");
        patientToCreate.setLastName("Patient");
        patientToCreate.setBirthDate(java.time.LocalDate.of(1990, 1, 1));
        patientToCreate.setGender("M");

        String json = """
            {
                "id": 5,
                "firstName": "New",
                "lastName": "Patient",
                "birthDate": "1990-01-01",
                "gender": "M",
                "address": null,
                "phoneNumber": null
            }
            """;

        server.expect(requestTo("http://localhost:8081/patients"))
                .andExpect(method(org.springframework.http.HttpMethod.POST))
                .andRespond(withSuccess(
                        json,
                        org.springframework.http.MediaType.APPLICATION_JSON
                ));

        Patient createdPatient = patientClient.createPatient(patientToCreate);

        assertThat(createdPatient.getId()).isEqualTo(5);
        assertThat(createdPatient.getFirstName()).isEqualTo("New");
        assertThat(createdPatient.getLastName()).isEqualTo("Patient");

        server.verify();
    }
}
