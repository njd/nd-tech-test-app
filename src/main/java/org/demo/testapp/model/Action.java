package org.demo.testapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Action {
    @Id
    @Column(name = "entity_id", nullable = false)
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;

    @Column(name = "id", nullable = false)
    private UUID id = UUID.randomUUID();

    @Size(max = 255)
    @NotNull
    @Column(name = "module_id", nullable = false)
    private String moduleId = "";

    @Size(max = 255)
    @NotNull
    @Column(name = "context", nullable = false)
    private String context = "";

    @Size(max = 255)
    @NotNull
    @Column(name = "activity", nullable = false)
    private String activity = "";

    @NotNull
    @Column(name = "when_recorded", nullable = false)
    private LocalDateTime whenRecorded = LocalDateTime.now();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_entity_id", nullable = false)
    private Patient patient;

    @Version
    @NotNull
    @Column(name = "entity_version", nullable = false)
    private Long entityVersion;

    @NotNull
    @Column(name = "entity_updated", nullable = false)
    private Instant entityUpdated = Instant.now();

    @NotNull
    @Column(name = "entity_created", nullable = false)
    private Instant entityCreated = Instant.now();

    public Action(Long entityId) {
        this.entityId = entityId;
    }
}
