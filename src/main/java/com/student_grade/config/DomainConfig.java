package com.student_grade.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.gav.students_rate.student_grade.model")
@EnableJpaRepositories("com.gav.students_rate.student_grade.repository")
@EnableTransactionManagement
public class DomainConfig {
}
