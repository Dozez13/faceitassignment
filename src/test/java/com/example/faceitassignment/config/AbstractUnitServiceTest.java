package com.example.faceitassignment.config;


import com.example.faceitassignment.core.dto.OrderPostDto;
import com.example.faceitassignment.domain.entity.DishOrderItem;
import com.example.faceitassignment.domain.entity.DrinkAdditionOrderItem;
import com.example.faceitassignment.domain.entity.DrinkOrderItem;
import com.example.faceitassignment.domain.entity.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractUnitServiceTest {


    protected OrderPostDto createOrderPostDto() {
        OrderPostDto orderPostDto = new OrderPostDto();
        orderPostDto.setMainCourseId(1L);
        orderPostDto.setDessertId(2L);
        orderPostDto.setDrinkId(1L);
        orderPostDto.setDrinkAdditionsIds(Arrays.asList(1L, 2L));
        return orderPostDto;
    }

    protected OrderPostDto createOrderPostDto(Long mainCourseId, Long dessertId, Long drinkId, Long... drinkAdditionIds) {
        OrderPostDto orderPostDto = new OrderPostDto();
        orderPostDto.setMainCourseId(mainCourseId);
        orderPostDto.setDessertId(dessertId);
        orderPostDto.setDrinkId(drinkId);
        orderPostDto.setDrinkAdditionsIds(Arrays.asList(drinkAdditionIds));
        return orderPostDto;
    }
    protected Order createOrder() {
        Order order = new Order();
        DishOrderItem mainCourseOrderItem = new DishOrderItem();
        mainCourseOrderItem.setDishId(1L);
        DishOrderItem dessertOrderItem = new DishOrderItem();
        dessertOrderItem.setDishId(2L);
        List<DrinkAdditionOrderItem> drinkAdditionOrderItems = Stream.of(1L, 2L)
                .map(drinkAdditionId -> {
                    DrinkAdditionOrderItem drinkAdditionOrderItem = new DrinkAdditionOrderItem();
                    drinkAdditionOrderItem.setDrinkAdditionId(drinkAdditionId);
                    return drinkAdditionOrderItem;
                })
                .collect(Collectors.toList());
        DrinkOrderItem drinkOrderItem = new DrinkOrderItem();
        drinkOrderItem.setDrinkId(1L);
        drinkOrderItem.setDrinkAdditionOrderItems(drinkAdditionOrderItems);
        order.setDishOrderItems(Arrays.asList(mainCourseOrderItem, dessertOrderItem));
        order.setDrinkOrderItems(Collections.singletonList(drinkOrderItem));
        return order;
    }

    protected Order createOrder(Long mainCourseId, Long dessertId, Long drinkId, Long... drinkAdditionIds) {
        Order order = new Order();
        DishOrderItem mainCourseOrderItem = new DishOrderItem();
        mainCourseOrderItem.setDishId(mainCourseId);
        DishOrderItem dessertOrderItem = new DishOrderItem();
        dessertOrderItem.setDishId(dessertId);
        List<DrinkAdditionOrderItem> drinkAdditionOrderItems = Stream.of(drinkAdditionIds)
                .map(drinkAdditionId -> {
                    DrinkAdditionOrderItem drinkAdditionOrderItem = new DrinkAdditionOrderItem();
                    drinkAdditionOrderItem.setDrinkAdditionId(drinkAdditionId);
                    return drinkAdditionOrderItem;
                })
                .collect(Collectors.toList());
        DrinkOrderItem drinkOrderItem = new DrinkOrderItem();
        drinkOrderItem.setDrinkId(drinkId);
        drinkOrderItem.setDrinkAdditionOrderItems(drinkAdditionOrderItems);
        order.setDishOrderItems(Arrays.asList(mainCourseOrderItem, dessertOrderItem));
        order.setDrinkOrderItems(Collections.singletonList(drinkOrderItem));
        return order;
    }




}
