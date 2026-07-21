package org.demo.testapp.repository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.demo.testapp.config.JpaTestConfig;
import org.demo.testapp.model.Action;
import org.demo.testapp.model.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the Patient repository.
 */
@Slf4j
@Import(JpaTestConfig.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientRepositoryIT {

    protected final Long TEST_PATIENT_ENTITY_ID = 1001L;

    private Patient patient;

    private Long testPatientEntityId;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {

        // prepare expected data
        UUID testPatientUuid = UUID.randomUUID();

        Patient patientToSave = new Patient("Mr", "Fred", "Bloggs");
        patientToSave.setId(testPatientUuid);
        patientToSave.setEntityId(TEST_PATIENT_ENTITY_ID);
        patientToSave.setWhenInvited(LocalDateTime.now().minusDays(1L));
        patientToSave.setEntityCreated(Instant.now());
        patientToSave.setEntityUpdated(Instant.now());
        patientToSave.setEntityVersion(null);

        Action action1 = new Action(1L);
        action1.setPatient(patientToSave);
        action1.setId(UUID.randomUUID());
        action1.setActivity("Activity 1");

        Action action2 = new Action(2L);
        action2.setPatient(patientToSave);
        action2.setId(UUID.randomUUID());
        action2.setActivity("Activity 2");

        patientToSave.setActions(List.of(action1, action2));

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
        assertThat(registered.size()).isEqualTo(1);
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
        assertThat(invited.size()).isEqualTo(1);
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
        assertThat(discharged.size()).isEqualTo(1);
    }

    @Test
    @SneakyThrows
    void findAll() {
        List<Patient> patients = patientRepository.findAll();
        assertThat(patients.size()).isEqualTo(1);
    }

    @Test
    void findById() {
        Patient found = patientRepository.findById(testPatientEntityId).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getFamilyName()).isEqualTo("Bloggs");
        assertThat(found.getActions().size()).isEqualTo(2);
    }

    @Test
    void findById_has_actions() {
        Patient found = patientRepository.findById(testPatientEntityId).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getActions().size()).isEqualTo(2);
    }
}