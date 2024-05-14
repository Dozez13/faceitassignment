package com.example.faceitassignment.core.mapper;

import com.example.faceitassignment.core.dto.OrderPostDto;
import com.example.faceitassignment.domain.entity.Order;

public interface OrderMapper {

    Order fromOrderPostDto(OrderPostDto orderPostDto);
}
