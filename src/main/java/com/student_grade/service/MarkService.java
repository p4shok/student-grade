package com.student_grade.service;

import com.student_grade.model.Mark;
import com.student_grade.model.MarksKey;
import com.student_grade.mappers.MarkMapper;
import com.student_grade.dto.GradeDTO;
import com.student_grade.repository.MarkRepository;
import com.student_grade.repository.StudentRepository;
import com.student_grade.repository.SubjectRepository;
import com.student_grade.util.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkService {

    final MarkRepository _markRep;
    final MarkMapper _mapper;
    Logger logger = LoggerFactory.getLogger(MarkService.class);

    private final SubjectRepository _subjectRepository;

    private final StudentRepository _studentRepository;


    public MarkService(MarkRepository markRepository, MarkMapper mapper, SubjectRepository subjectRepository, StudentRepository studentRepository) {
        _markRep = markRepository;
        _mapper = mapper;
        _subjectRepository = subjectRepository;
        _studentRepository = studentRepository;
    }

    public List<Mark> getAll() {
        return _markRep.findAll();
    }

    public List<?> getByRate() {
        return _markRep.getAllByRate();
    }

    public void create(GradeDTO gradeDTO) throws NotFoundException {
        Mark mark = _mapper.markDtoToMark(gradeDTO);
        mark.setId(new MarksKey());
        if (!_studentRepository.existsById(mark.getStudent().getId())||
                !_subjectRepository.existsById(mark.getSubject().getId()))
            throw new NotFoundException("Студент или преподаватель не найдены");
        _markRep.save(mark);
    }
}
