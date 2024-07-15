package com.student_grade.responses;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class BadResponse extends ResponseEntity<String> {
    public BadResponse(String body, HttpStatusCode status) {
        super(body, status);
    }
}
