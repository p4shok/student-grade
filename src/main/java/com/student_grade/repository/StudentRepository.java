package com.student_grade.repository;

import com.student_grade.model.ExtendedStudent;
import com.student_grade.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select new com.gav.students_rate.student_grade.domain.ExtendedStudent(s, sub, m)" +
            "  from Student s join Mark m on s.id=m.student.id " +
            "join Subject sub on m.subject.id=sub.id " +
            "where s.fullname=:fullname")
    List<ExtendedStudent> customGetStudentByFullname(@Param("fullname") String fullname);

    @Query("select avg(case when s.fullname=:fullname then m.mark end ) from Student s join Mark m on s.id=m.student.id")
    Double customAverageRate(@Param("fullname") String fullname);

    boolean existsByEmailOrPhone(String email, String phone);

    boolean existsByFullname(String fullname);

    @Transactional
    @Modifying
    @Query(value = "INSERT into students(fullname, age, email, phone)" +
            " values (:#{#student.fullname}," +
            ":#{#student.age},:#{#student.email}, :#{#student.phone});"
            , nativeQuery = true)
    void customSave(@Param("student") Student student);

    @Query("SELECT currval('students_id_seq')")
    Long selectLastStudentId();

    @Query("SELECT s FROM Student s ORDER BY RANDOM() LIMIT 1")
    Student random();
}
