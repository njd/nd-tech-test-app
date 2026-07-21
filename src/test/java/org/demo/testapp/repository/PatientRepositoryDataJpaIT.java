package org.demo.testapp.repository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.demo.testapp.config.JpaTestConfig;
import org.demo.testapp.model.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This is a DataJpaTest, a suite of integration tests against an existing
 * populated database. Since a DataJpaTest runs every test in an implicit
 * transaction and then rolls it back, we first gather counts of the existing
 * records: all records, registered, invited, discharged.
 * Then in each test, we can check how many more we've added, in the test
 * and in the setUp.
 * These tests use the application-datajpatest.properties for config.
 * Yes, I loaded the data into an external postgres database to run these.
 */
@Slf4j
@Import(JpaTestConfig.class)
@ActiveProfiles({"test", "datajpatest"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientRepositoryDataJpaIT {

    /* keep counts of record types for later comparison */
    record DataStats (
        long records,
        long registered,
        long invited,
        long discharged
    ) {
    }

    DataStats initialStats;

    protected final Long TEST_PATIENT_ENTITY_ID = 1001L;

    private Patient patient;
    private Patient patientToSave;

    private UUID testPatientUuid;
    private Long testPatientEntityId;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {

        long records = patientRepository.count();
        long registered = patientRepository.countPatientByWhenRegisteredIsNotNull();
        long invited = patientRepository.countPatientByWhenInvitedIsNotNull();
        long discharged = patientRepository.countPatientByWhenDischargedIsNotNull();

        initialStats = new DataStats(records, registered, invited, discharged);

        // prepare expected data
        testPatientUuid = UUID.randomUUID();

        patientToSave = new Patient("Mr", "Fred", "Bloggs");
        patientToSave.setId(testPatientUuid);
        patientToSave.setEntityId(TEST_PATIENT_ENTITY_ID);
        patientToSave.setWhenInvited(LocalDateTime.now().minusDays(1L));
        patientToSave.setEntityCreated(Instant.now());
        patientToSave.setEntityUpdated(Instant.now());
        patientToSave.setEntityVersion(null);

        try {
            patient = patientRepository.save(patientToSave);
            testPatientEntityId = patient.getEntityId();
        } catch (Exception e) {
            log.error("Failed to set up patient record: ", e);
        }
    }

    @AfterEach
    void tearDown() {
        if (patient != null) {
            patientRepository.delete(patient);
        }
    }

    @Test
    @SneakyThrows
    void findByWhenRegistered() {
        // given
        patient.setWhenRegistered(LocalDateTime.now());
        patientRepository.save(patient);

        // when
        List<Patient> registered = patientRepository.findByWhenRegisteredIsNotNull();

        // then
        assertThat(registered.size()).isEqualTo(initialStats.registered() + 1);
    }

    @Test
    @SneakyThrows
    void findByWhenInvited() {
        // given
        patient.setWhenInvited(LocalDateTime.now());
        patientRepository.save(patient);

        // when
        List<Patient> invited = patientRepository.findByWhenInvitedIsNotNull();

        // then
        assertThat(invited.size()).isEqualTo(initialStats.invited + 1);
    }

    @Test
    @SneakyThrows
    void findByWhenDischarged() {
        // given
        patient.setWhenDischarged(LocalDateTime.now());
        patientRepository.save(patient);

        // when
        List<Patient> discharged = patientRepository.findByWhenDischargedIsNotNull();

        // then
        assertThat(discharged.size()).isEqualTo(initialStats.discharged() + 1);
    }

    @Test
    @SneakyThrows
    void findAll() {
        List<Patient> patients = patientRepository.findAll();
        assertThat(patients.size()).isEqualTo(initialStats.records() + 1);
    }

    @Test
    void findById() {
        Patient found = patientRepository.findById(testPatientEntityId).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getFamilyName()).isEqualTo("Bloggs");
    }
}