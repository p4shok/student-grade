package com.student_grade.controller;

import com.student_grade.responses.BadResponse;
import com.student_grade.responses.OkResponse;
import com.student_grade.service.SubjectService;
import com.student_grade.util.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "subjects", description = "the subjects api")
@Controller
@RequestMapping("/api/subjects")
public class SubjectController {
    Logger logger = LoggerFactory.getLogger(SubjectController.class);
    private final SubjectService _subjectService;

    public SubjectController(SubjectService subjectService) {
        _subjectService = subjectService;
    }


    @Operation(summary = "delete subject on id", tags = "delete subject")
    @Parameter(schema = @Schema(implementation = Long.class), name = "id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "subject deleted",
                    content = {@Content(schema = @Schema(implementation = OkResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "subject not found",
                    content = {@Content(schema = @Schema(implementation = BadResponse.class))})})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            _subjectService.delete(id);
            logger.info("Предмет с id " + id + " успешно удален");
            return new OkResponse("Предмет успешно удален", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.info("Операция завершена неудачно");
            return new BadResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
