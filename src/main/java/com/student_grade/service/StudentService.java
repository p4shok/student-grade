package com.student_grade.service;

import com.student_grade.model.ExtendedStudent;
import com.student_grade.model.Mark;
import com.student_grade.model.Student;
import com.student_grade.model.Subject;
import com.student_grade.dto.StudentWithMarks;
import com.student_grade.mappers.StudentMapper;
import com.student_grade.dto.StudentDTO;
import com.student_grade.repository.StudentRepository;
import com.student_grade.util.AlreadyExistsException;
import com.student_grade.util.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper _mapper;

    public StudentService(final StudentRepository studentRepository, StudentMapper mapper) {
        this.studentRepository = studentRepository;
        _mapper=mapper;
    }

    public List<StudentDTO> findAll() {
        final List<Student> students = studentRepository.findAll(Sort.by("id"));
        return students.stream()
                .map(_mapper::studentToStudentDto)
                .toList();
    }

    public StudentDTO get(final Long id) {
        return studentRepository.findById(id)
                .map(_mapper::studentToStudentDto)
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final StudentDTO studentDTO) throws AlreadyExistsException {
        if (studentRepository.existsByEmailOrPhone(studentDTO.getEmail(), studentDTO.getPhone())){
            throw new AlreadyExistsException("Пользователь с такими данными уже существует");
        }
        final Student student = _mapper.studentDtoToStudent(studentDTO);
        studentRepository.customSave(student);
        return studentRepository.selectLastStudentId();
    }

    public void update(final Long id, final StudentDTO studentDTO) throws NotFoundException{
         Student student = studentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        student = _mapper.studentDtoToStudent(studentDTO);
        studentRepository.save(student);
    }

    public StudentWithMarks getStudentByFullname(String fullname) throws NotFoundException{
        if (studentRepository.existsByFullname(fullname)) {
            List<ExtendedStudent> extendedStudents = studentRepository.customGetStudentByFullname(fullname);
            Logger loggerFactory = LoggerFactory.getLogger(StudentService.class);
            extendedStudents.forEach(extendedStudent -> loggerFactory.info(extendedStudent.get_subject().getName()));
            List<Mark> markList =
                    extendedStudents.stream().map(ExtendedStudent::get_mark).toList();
            List<Subject> subjectList =
                    extendedStudents.stream().map(ExtendedStudent::get_subject).toList();
            StudentWithMarks studentWithMarks = new StudentWithMarks(extendedStudents.get(0).get_student(), subjectList, markList);
            studentWithMarks.set_average(studentRepository.customAverageRate(fullname));
            return studentWithMarks;
        }else throw new NotFoundException("Пользователь с именем: "+fullname+" не найден");
    }

    public void delete(final Long id) {
        Optional<Student> subject =
                studentRepository.findById(id);
        Consumer<Student> consumer = studentRepository::delete;
        subject.ifPresentOrElse(consumer, ()-> {
            throw new NotFoundException("Студент не найден");});
    }



}
