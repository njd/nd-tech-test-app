package org.demo.testapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Action {
    @Id
    @Column(name = "entity_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;

    @Size(max = 255)
    @NotNull
    @Column(name = "module_id", nullable = false)
    private String moduleId;
    @Size(max = 255)
    @NotNull
    @Column(name = "context", nullable = false)
    private String context;
    @Size(max = 255)
    @NotNull
    @Column(name = "activity", nullable = false)
    private String activity;
    @NotNull
    @Column(name = "when_recorded", nullable = false)
    private Instant whenRecorded;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_entity_id", nullable = false)
    private Patient patientEntity;
    @NotNull
    @Column(name = "entity_version", nullable = false)
    private Long entityVersion;
    @NotNull
    @Column(name = "entity_updated", nullable = false)
    private Instant entityUpdated;
    @NotNull
    @Column(name = "entity_created", nullable = false)
    private Instant entityCreated;


}
