package com.student_grade.model;

import lombok.Getter;

@Getter
public class ExtendedStudent {
    private Student _student;
    private Subject _subject;
    private Mark _mark;
    private Double _average;

    public ExtendedStudent(Student student, Subject subjects
            , Mark marks){
        _student=student;
        _subject=subjects;
        _mark=marks;

    }

    public void set_average(Double average){
        _average=average;
    }
}
