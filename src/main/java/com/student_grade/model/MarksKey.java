package com.student_grade.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
public class MarksKey implements Serializable {

        @Column(name = "student")
        Long student;

        @Column(name = "object")
        Long object;


        @Override
        public String toString() {
                return student+" "+ object;
        }
}
