package com.student_grade.mappers;

import com.student_grade.model.Mark;
import com.student_grade.dto.GradeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MarkMapper {

    GradeDTO markToMarkDto(Mark mark);
    @Mapping(target = "id", ignore = true)
    Mark markDtoToMark(GradeDTO gradeDTO);
}
