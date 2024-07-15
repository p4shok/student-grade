package com.student_grade.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "students")
@NoArgsConstructor
public class Student {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(name = "group_id")
    private Long group;

    @Column
    private String fullname;

    @Column
    private Integer age;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Mark> marks;
}
