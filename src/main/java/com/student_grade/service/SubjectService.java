package com.student_grade.service;

import com.student_grade.model.Subject;
import com.student_grade.mappers.SubjectMapper;
import com.student_grade.repository.SubjectRepository;
import com.student_grade.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class SubjectService {
    final SubjectRepository _objectRepository;
    private final SubjectMapper _subjectMapper;

    public SubjectService(SubjectRepository objectRepository, SubjectMapper subjectMapper) {
        _objectRepository = objectRepository;
        _subjectMapper=subjectMapper;
    }

    public void create(Subject s) {
        _objectRepository.save(s);
    }

    public List<Subject> getAll() {
        return _objectRepository.findAll();
    }

    public void delete(Long id) throws NotFoundException{
        Optional<Subject> subject =
            _objectRepository.findById(id);
        Consumer<Subject> consumer = _objectRepository::delete;
                subject.ifPresentOrElse(consumer, ()-> {
            throw new NotFoundException("Предмет не найден");});
    }
}
