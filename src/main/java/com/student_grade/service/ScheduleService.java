package com.student_grade.service;

import com.student_grade.model.Student;
import com.student_grade.model.Subject;
import com.student_grade.dto.GradeDTO;
import com.student_grade.repository.StudentRepository;
import com.student_grade.repository.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    final StudentRepository studentRepository;

    final MarkService markService;

    final SubjectRepository subjectRepository;

    final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    public ScheduleService(MarkService markService,
                           StudentRepository studentRepository,
                           SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.markService = markService;
    }

    @Scheduled(fixedDelay = 10000L)
    public void insertMarks() {

        GradeDTO gradeDTO = new GradeDTO();
        Student student = studentRepository.random();
        Subject subject = subjectRepository.random();
        gradeDTO.setStudent(student);
        gradeDTO.setSubject(subject);
        gradeDTO.setMark((int) ((Math.random() * (100 - 0)) + 0));
        markService.create(gradeDTO);
        logger.info("Рандомно проставлена оценка студенту: "
                + student.getFullname() + " по предмету "
                + subject.getName());

    }
}
