package com.example.faceitassignment.core.service.impl;

import com.example.faceitassignment.core.dto.OrderPostDto;
import com.example.faceitassignment.core.exception.DishNotFoundException;
import com.example.faceitassignment.core.exception.DrinkAdditionNotFoundException;
import com.example.faceitassignment.core.exception.DrinkNotFoundException;
import com.example.faceitassignment.core.mapper.OrderMapper;
import com.example.faceitassignment.core.service.MessageService;
import com.example.faceitassignment.core.service.OrderService;
import com.example.faceitassignment.domain.entity.Order;
import com.example.faceitassignment.domain.repository.DishRepository;
import com.example.faceitassignment.domain.repository.DrinkAdditionRepository;
import com.example.faceitassignment.domain.repository.DrinkRepository;
import com.example.faceitassignment.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final MessageService messageService;
    private final OrderMapper orderMapper;
    private final DishRepository dishRepository;
    private final DrinkRepository drinkRepository;
    private final DrinkAdditionRepository drinkAdditionRepository;
    private final OrderRepository orderRepository;

    @Override
    public void makeOrder(OrderPostDto orderPostDto) {
        if (!dishRepository.existsById(orderPostDto.getMainCourseId())) {
            throw new DishNotFoundException(messageService.getMessage("dish.with.such.id.does.not.exist"));
        }
        if (!dishRepository.existsById(orderPostDto.getDessertId())) {
            throw new DishNotFoundException(messageService.getMessage("dish.with.such.id.does.not.exist"));
        }
        if (!drinkRepository.existsById(orderPostDto.getDrinkId())) {
            throw new DrinkNotFoundException(messageService.getMessage("drink.with.such.id.does.not.exist"));
        }
        if (!orderPostDto.getDrinkAdditionsIds().stream().allMatch(drinkAdditionRepository::existsById)) {
            throw new DrinkAdditionNotFoundException(messageService.getMessage("drink.addition.with.such.id.does.not.exist"));
        }
        Order order = orderMapper.fromOrderPostDto(orderPostDto);
        orderRepository.save(order);
    }
}
