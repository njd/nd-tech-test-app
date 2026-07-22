package org.demo.testapp.service;

import org.demo.testapp.model.Patient;
import org.demo.testapp.repository.ActionRepository;
import org.demo.testapp.repository.PatientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    ActionRepository actionRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeAll
    public static void beforeAll() {
        MockitoAnnotations.openMocks(PatientServiceTest.class);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPatient_byEntityId_succeeds() throws PatientNotFoundException {
        Long entityId = 1001L;
        UUID uuid = UUID.randomUUID();
        Patient mockPatient = new Patient("Mr", "Firstname", "Lastname");
        mockPatient.setEntityId(entityId);
        mockPatient.setId(uuid);
        when(patientRepository.findByEntityId(anyLong())).thenReturn(Optional.of(mockPatient));

        Patient patient = patientService.getPatient(entityId);

        assertThat(patient.getEntityId()).isEqualTo(entityId);
        assertThat(patient.getGivenName()).isEqualTo("Firstname");
        assertThat(patient.getFamilyName()).isEqualTo("Lastname");
    }

    @Test
    void getPatient_byEntityId_missing_fails() {
        Long entityId = 1001L;
        UUID uuid = UUID.randomUUID();
        Patient mockPatient = new Patient("Mr", "Firstname", "Lastname");
        mockPatient.setEntityId(entityId);
        mockPatient.setId(uuid);
        when(patientRepository.findByEntityId(anyLong())).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientService.getPatient(entityId));
    }

    @Test
    void getPatients() {
        List<Patient> mockPatientList = List.of(
                new Patient("Mr", "Firstname", "Lastname"),
                new Patient("Miss", "A", "B")
        );

        when(patientRepository.findAll()).thenReturn(mockPatientList);

        List<Patient> patients = patientService.getPatients();

        assertThat(patients).hasSize(mockPatientList.size());
    }

    @Test
    void getPatientsRegistered() {
        LocalDateTime registered = LocalDateTime.now();

        List<Patient> mockPatientList = List.of(
                new Patient("Mr", "Firstname", "Lastname", registered, null, null),
                new Patient("Miss", "A", "B", registered, null, null)
        );

        when(patientRepository.findByWhenRegisteredIsNotNull()).thenReturn(mockPatientList);

        List<Patient> patientDtos = patientService.getPatientsRegistered();

        assertThat(patientDtos).hasSize(2);
    }

    @Test
    void getPatientsInvited() {
        LocalDateTime invited = LocalDateTime.now().minusDays(1L);

        List<Patient> mockPatientList = List.of(
                new Patient("Mr", "Firstname", "Lastname", null, invited, null),
                new Patient("Miss", "A", "B", null, invited, null)
        );

        when(patientRepository.findByWhenInvitedIsNotNull()).thenReturn(mockPatientList);

        List<Patient> patients = patientService.getPatientsInvited();

        assertThat(patients).hasSize(2);
    }

    @Test
    void getPatientsDischarged() {
        LocalDateTime discharged = LocalDateTime.now();

        List<Patient> mockPatientList = List.of(
                new Patient("Mr", "Firstname", "Lastname", null, null, discharged),
                new Patient("Miss", "A", "B", null, null, discharged)
        );

        when(patientRepository.findByWhenDischargedIsNotNull()).thenReturn(mockPatientList);

        List<Patient> patients = patientService.getPatientsDischarged();

        assertThat(patients).hasSize(2);
    }
}