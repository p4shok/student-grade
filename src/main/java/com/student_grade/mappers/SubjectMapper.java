package com.student_grade.mappers;

import com.student_grade.model.Subject;
import com.student_grade.dto.SubjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubjectMapper {

    Subject subjectDtoToSubject(SubjectDto subjectDto);

    SubjectDto subjectToSubjectDto(Subject subject);
}
