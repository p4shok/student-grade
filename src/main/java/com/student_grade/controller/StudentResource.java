package com.student_grade.controller;

import com.student_grade.logger.LoggerStatuses;
import com.student_grade.dto.StudentDTO;
import com.student_grade.responses.BadResponse;
import com.student_grade.responses.OkResponse;
import com.student_grade.service.StudentService;
import com.student_grade.util.AlreadyExistsException;
import com.student_grade.util.messages.LoggerMessage;
import com.student_grade.util.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentResource {

    private final StudentService studentService;

    Logger logger = LoggerFactory.getLogger(StudentResource.class);

    public StudentResource(final StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @Operation(summary = "get student on id", tags = "student")
    @Parameter(schema = @Schema(implementation = String.class), name = "fullname")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "student object",
                    content = {@Content(schema = @Schema(implementation = OkResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "student not found",
                    content = {@Content(schema = @Schema(implementation = BadResponse.class))})})
    @GetMapping("/{fullname}")
    public ResponseEntity<?>
    getByFullname(@PathVariable(name = "fullname") String fullname) {
        try {
            logger.info(new LoggerMessage(LoggerStatuses.OK, "Студент успешно возвращен").toString());
            return new ResponseEntity<>(studentService.getStudentByFullname(fullname), HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.info(new LoggerMessage(LoggerStatuses.BAD, e.getMessage()).toString());
            return new BadResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "create student", tags = "student")
    @Parameter(schema = @Schema(implementation = StudentDTO.class), name = "StudentDTO")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "student created",
                    content = {@Content(schema = @Schema(implementation = OkResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "student already exists",
                    content = {@Content(schema = @Schema(implementation = BadResponse.class))})})
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody @Valid final StudentDTO studentDTO) {
        final Long createdId;
        try {
            createdId = studentService.create(studentDTO);
            logger.info(new LoggerMessage(LoggerStatuses.OK, "Студент успешно создан, его id: " + createdId).toString());
            return new OkResponse("Студент успешно создан, его id: " + createdId, HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            logger.info(new LoggerMessage(LoggerStatuses.BAD, e.getMessage()).toString());
            return new BadResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(summary = "update student on id", tags = "update student")
    @Parameter(schema = @Schema(implementation = Long.class), name = "id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = {
                    @Content(
                            schema = @Schema(implementation = StudentDTO.class))})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "student updated",
                    content = {@Content(schema = @Schema(implementation = OkResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "student not found",
                    content = {@Content(schema = @Schema(implementation = BadResponse.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable(name = "id") final Long id,
                                           @RequestBody @Valid final StudentDTO studentDTO) {
        LoggerMessage loggerMessage = new LoggerMessage();
        try {
            studentService.update(id, studentDTO);
            loggerMessage.message("Студент с id: " + id + " успешно обновлен").
                    status(LoggerStatuses.OK);
            return new OkResponse("Студент с id: " + id + " успешно обновлен", HttpStatus.OK);
        } catch (NotFoundException e) {
            loggerMessage.message("Операция завершена неудачно").
                    status(LoggerStatuses.BAD);
            return new BadResponse("Операция завершена неудачно", HttpStatus.BAD_REQUEST);
        } finally {
            logger.info(loggerMessage.toString());
        }

    }

    @Operation(summary = "delete student on id", tags = "delete student")
    @Parameter(schema = @Schema(implementation = Long.class), name = "id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "student deleted",
                    content = {@Content(schema = @Schema(implementation = OkResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "student not found",
                    content = {@Content(schema = @Schema(implementation = BadResponse.class))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(name = "id") final Long id) {

        LoggerMessage loggerMessage = new LoggerMessage();
        try {
            studentService.delete(id);
            loggerMessage.message("Студент успешно удален").
                    status(LoggerStatuses.OK);
            return new OkResponse("Студент успешно удален", HttpStatus.OK);
        } catch (NotFoundException e) {
            loggerMessage.message("Операция завершена неудачно").
                    status(LoggerStatuses.BAD);
            return new BadResponse("Операция завершена неудачно", HttpStatus.BAD_REQUEST);
        } finally {
            logger.info(loggerMessage.toString());
        }
    }

}
