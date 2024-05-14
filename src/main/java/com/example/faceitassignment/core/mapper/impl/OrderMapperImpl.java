package com.example.faceitassignment.core.mapper.impl;

import com.example.faceitassignment.core.dto.OrderPostDto;
import com.example.faceitassignment.core.mapper.OrderMapper;
import com.example.faceitassignment.domain.entity.DishOrderItem;
import com.example.faceitassignment.domain.entity.DrinkAdditionOrderItem;
import com.example.faceitassignment.domain.entity.DrinkOrderItem;
import com.example.faceitassignment.domain.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapperImpl implements OrderMapper {
    @Override
    public Order fromOrderPostDto(OrderPostDto orderPostDto) {
        if (orderPostDto == null) {
            return null;
        }
        Order order = new Order();
        DishOrderItem mainCourseOrderItem = new DishOrderItem();
        mainCourseOrderItem.setDishId(orderPostDto.getMainCourseId());
        DishOrderItem dessertOrderItem = new DishOrderItem();
        dessertOrderItem.setDishId(orderPostDto.getDessertId());
        List<DrinkAdditionOrderItem> drinkAdditionOrderItems = orderPostDto.getDrinkAdditionsIds()
                .stream()
                .map(drinkAdditionId -> {
                    DrinkAdditionOrderItem drinkAdditionOrderItem = new DrinkAdditionOrderItem();
                    drinkAdditionOrderItem.setDrinkAdditionId(drinkAdditionId);
                    return drinkAdditionOrderItem;
                })
                .collect(Collectors.toList());
        DrinkOrderItem drinkOrderItem = new DrinkOrderItem();
        drinkOrderItem.setDrinkId(orderPostDto.getDrinkId());
        drinkOrderItem.setDrinkAdditionOrderItems(drinkAdditionOrderItems);
        order.setDishOrderItems(Arrays.asList(mainCourseOrderItem, dessertOrderItem));
        order.setDrinkOrderItems(Collections.singletonList(drinkOrderItem));
        return order;
    }
}
