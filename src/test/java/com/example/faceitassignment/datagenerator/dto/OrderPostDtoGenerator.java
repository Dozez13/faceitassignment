package com.example.faceitassignment.datagenerator.dto;

import com.example.faceitassignment.core.dto.OrderPostDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class OrderPostDtoGenerator {
    public OrderPostDto orderPostDto() {
        OrderPostDto orderPostDto = new OrderPostDto();
        orderPostDto.setMainCourseId(1L);
        orderPostDto.setDessertId(2L);
        orderPostDto.setDrinkId(1L);
        orderPostDto.setDrinkAdditionsIds(Arrays.asList(1L, 2L));
        return orderPostDto;
    }

    public OrderPostDto orderPostDto(Long mainCourseId, Long dessertId, Long drinkId, Long... drinkAdditionIds) {
        OrderPostDto orderPostDto = new OrderPostDto();
        orderPostDto.setMainCourseId(mainCourseId);
        orderPostDto.setDessertId(dessertId);
        orderPostDto.setDrinkId(drinkId);
        orderPostDto.setDrinkAdditionsIds(Arrays.asList(drinkAdditionIds));
        return orderPostDto;
    }
}
