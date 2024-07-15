package com.student_grade.controller;

import com.student_grade.logger.LoggerStatuses;
import com.student_grade.dto.GradeDTO;
import com.student_grade.responses.BadResponse;
import com.student_grade.responses.OkResponse;
import com.student_grade.service.MarkService;
import com.student_grade.util.messages.LoggerMessage;
import com.student_grade.util.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/marks")
public class MarksController {

    Logger logger = LoggerFactory.getLogger(MarkService.class);
    private MarkService _markService;

    public MarksController(MarkService markService) {
        _markService = markService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(_markService.getAll(), HttpStatus.OK);
    }
    @Operation(summary = "get rate of students ", tags = "rate")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "return rate",
                    content = {@Content(schema = @Schema(implementation = OkResponse.class))}),
            })
    @GetMapping(value = "/rate")
    public ResponseEntity<?> getByRate() {
        return new ResponseEntity<>(_markService.getByRate(), HttpStatus.OK);
    }

    @Operation(summary = "create mark ", tags = "create mark")
    @Parameter(schema = @Schema(implementation = GradeDTO.class), name = "markDTO")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "mark created",
                    content = {@Content(schema = @Schema(implementation = OkResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "user or subject not found",
                    content = {@Content(schema = @Schema(implementation = BadResponse.class))})})
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody GradeDTO gradeDTO) {
        try {
            _markService.create(gradeDTO);
            logger.info(new LoggerMessage(LoggerStatuses.OK, "Успешно создано").toString());
            return new OkResponse("Успешно создано", HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.info(new LoggerMessage(LoggerStatuses.BAD, e.getMessage()).toString());
            return new BadResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
