package com.student_grade.repository;

import com.student_grade.model.Mark;
import com.student_grade.model.MarksKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, MarksKey> {
    @Query("SELECT s.fullname, avg(m.mark) as average from Student s join Mark m on s.id=m.student.id group by (s .fullname) order by average desc ")
    List<?> getAllByRate();
}
