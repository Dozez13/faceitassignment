package com.example.faceitassignment.domain.repository;

import com.example.faceitassignment.domain.entity.DrinkOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkOrderItemRepository extends JpaRepository<DrinkOrderItem, Long> {
}
