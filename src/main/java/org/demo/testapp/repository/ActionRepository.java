package org.demo.testapp.repository;

import org.demo.testapp.model.Action;
import org.demo.testapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {

    List<Action> findByPatient(Patient patient);

    // for test support

    void deleteAll();
}
