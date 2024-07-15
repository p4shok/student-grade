package com.student_grade.dto;

import com.student_grade.model.Mark;
import com.student_grade.model.Student;
import com.student_grade.model.Subject;
import lombok.Getter;

import java.util.List;
@Getter
public class StudentWithMarks {
    private Student _student;

    private List<Subject> _subjectList;

    private List<Mark> _markList;
    private Double _average;

    public StudentWithMarks(Student student,
                            List<Subject> subjectList, List<Mark> markList){
        _student=student;
        _subjectList=subjectList;
        _markList=markList;

    }
    public void set_average(Double average){
        _average=average;
    }
}
