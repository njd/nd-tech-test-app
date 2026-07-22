package org.demo.testapp.service;

import org.demo.testapp.model.Patient;
import org.demo.testapp.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient getPatient(Long entityId) throws PatientNotFoundException {
        Optional<Patient> patient = patientRepository.findByEntityId(entityId);

        return patient.orElseThrow(() -> new PatientNotFoundException(entityId));

    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public List<Patient> getPatientsRegistered() {
        return patientRepository.findByWhenRegisteredIsNotNull();

    }

    public List<Patient> getPatientsInvited() {
        return patientRepository.findByWhenInvitedIsNotNull();
    }

    public List<Patient> getPatientsDischarged() {
        return patientRepository.findByWhenDischargedIsNotNull();
    }
}

