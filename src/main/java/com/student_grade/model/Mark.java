package com.student_grade.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "marks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mark {
    @EmbeddedId
    private MarksKey id;


    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("student")
    @JoinColumn(name = "student")
    Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("object")
    @JoinColumn(name = "object")
    Subject subject;

    Integer mark;


}
