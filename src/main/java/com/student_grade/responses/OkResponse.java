package com.student_grade.responses;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class OkResponse extends ResponseEntity<String> {

    public OkResponse(String body, HttpStatusCode status) {
        super(body, status);
    }
}
