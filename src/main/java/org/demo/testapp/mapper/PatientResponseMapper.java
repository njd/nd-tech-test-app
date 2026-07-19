package org.demo.testapp.mapper;

import org.demo.testapp.dto.PatientResponseDto;
import org.demo.testapp.model.Patient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientResponseMapper {
    PatientResponseDto toDto(Patient patient);

    Patient toEntity(PatientResponseDto dto);

    List<PatientResponseDto> toDtoList(List<Patient> patients);

}
