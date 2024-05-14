package com.example.faceitassignment.domain.repository;

import com.example.faceitassignment.domain.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
