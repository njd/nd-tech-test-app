package org.demo.testapp.repository;

import org.demo.testapp.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {

    List<Action> findByPatientId(Long patientId);
}
