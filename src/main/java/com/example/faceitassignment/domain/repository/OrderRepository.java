package com.example.faceitassignment.domain.repository;

import com.example.faceitassignment.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
