package org.demo.testapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Patient {

    @Id
    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Size(max = 255)
    @Column(name = "title")
    private String title;
    @Size(max = 255)
    @Column(name = "nhs_number")
    private String nhsNumber;
    @Size(max = 255)
    @Column(name = "hospital_id")
    private String hospitalId;
    @Size(max = 255)
    @NotNull
    @Column(name = "given_name", nullable = false)
    private String givenName;
    @Size(max = 255)
    @Column(name = "gender")
    private String gender;
    @Size(max = 255)
    @NotNull
    @Column(name = "family_name", nullable = false)
    private String familyName;
    @Column(name = "when_registered")
    private Instant whenRegistered;
    @NotNull
    @Column(name = "when_invited", nullable = false)
    private Instant whenInvited;
    @Column(name = "when_discharged")
    private Instant whenDischarged;
    @NotNull
    @Column(name = "entity_version", nullable = false)
    private Long entityVersion;
    @NotNull
    @Column(name = "entity_updated", nullable = false)
    private Instant entityUpdated;
    @NotNull
    @Column(name = "entity_created", nullable = false)
    private Instant entityCreated;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

}
