package com.student_grade.repository;

import com.student_grade.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("select count(*) from Subject ")
    Long countAll();

    @Query("SELECT s FROM Subject s ORDER BY RANDOM() LIMIT 1")
    Subject random();
}
