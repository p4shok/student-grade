package com.student_grade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling

public class StudentsApplication {

    public static void main(final String[] args) {
        SpringApplication.run(StudentsApplication.class, args);
    }

}
