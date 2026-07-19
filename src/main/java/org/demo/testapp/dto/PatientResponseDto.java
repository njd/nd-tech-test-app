package org.demo.testapp.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PatientResponseDto {

    public String title;
    public String givenName;
    public String familyName;
    LocalDateTime whenRegistered;
    LocalDateTime whenInvited;
    LocalDateTime whenDischarged;
    List<ActionDto> actions;
}
