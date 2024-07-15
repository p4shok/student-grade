package com.student_grade.mappers;

import com.student_grade.model.Student;
import com.student_grade.dto.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {
    StudentDTO studentToStudentDto(Student student);
Student studentDtoToStudent(StudentDTO studentDTO);
}
