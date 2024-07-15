package com.student_grade.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class StudentDTO {

    private Long id;

    private Long group;

    @Size(max = 255)
    private String fullname;
    @Min(value = 14)
    @Max(value = 100)
    private Integer age;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String phone;

}
