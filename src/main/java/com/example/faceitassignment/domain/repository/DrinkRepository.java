package com.example.faceitassignment.domain.repository;

import com.example.faceitassignment.domain.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
}
