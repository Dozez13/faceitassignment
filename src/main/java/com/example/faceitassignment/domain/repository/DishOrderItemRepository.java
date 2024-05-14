package com.example.faceitassignment.domain.repository;

import com.example.faceitassignment.domain.entity.DishOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishOrderItemRepository extends JpaRepository<DishOrderItem, Long> {
}
