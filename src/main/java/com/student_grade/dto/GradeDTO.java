package com.student_grade.dto;

import com.student_grade.model.Student;
import com.student_grade.model.Subject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GradeDTO {
    Student student;
    Subject subject;
    @Min(value = 0)
    @Max(value = 100)
    Integer mark;
}
