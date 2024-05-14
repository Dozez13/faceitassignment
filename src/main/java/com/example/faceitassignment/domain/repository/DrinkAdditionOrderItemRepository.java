package com.example.faceitassignment.domain.repository;

import com.example.faceitassignment.domain.entity.DrinkAdditionOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkAdditionOrderItemRepository extends JpaRepository<DrinkAdditionOrderItem, Long> {
}
