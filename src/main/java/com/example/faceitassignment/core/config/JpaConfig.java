package com.example.faceitassignment.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.example.faceitassignment.domain.repository")
@EntityScan("com.example.faceitassignment.domain.entity")
public class JpaConfig {
}
