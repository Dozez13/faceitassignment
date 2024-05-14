package com.example.faceitassignment.core.service;

import com.example.faceitassignment.config.AbstractUnitServiceTest;
import com.example.faceitassignment.core.dto.OrderPostDto;
import com.example.faceitassignment.core.exception.DishNotFoundException;
import com.example.faceitassignment.core.exception.DrinkAdditionNotFoundException;
import com.example.faceitassignment.core.exception.DrinkNotFoundException;
import com.example.faceitassignment.core.mapper.OrderMapper;
import com.example.faceitassignment.core.service.impl.OrderServiceImpl;
import com.example.faceitassignment.domain.entity.Order;
import com.example.faceitassignment.domain.repository.DishRepository;
import com.example.faceitassignment.domain.repository.DrinkAdditionRepository;
import com.example.faceitassignment.domain.repository.DrinkRepository;
import com.example.faceitassignment.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest extends AbstractUnitServiceTest {
    @Mock
    private MessageService messageService;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private DishRepository dishRepository;
    @Mock
    private DrinkRepository drinkRepository;
    @Mock
    private DrinkAdditionRepository drinkAdditionRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderServiceImpl orderServiceImpl;


    @Test
    void shouldCreateOrder() {
        //GIVEN
        Long mainCourseId = 1L;
        Long dessertId = 2L;
        Long drinkId = 1L;
        Long drinkAdditionId1 = 1L;
        Long drinkAdditionId2 = 2L;
        OrderPostDto orderPostDto = createOrderPostDto(mainCourseId, dessertId, drinkId, drinkAdditionId1, drinkAdditionId2);
        Order order = createOrder(mainCourseId, dessertId, drinkId, drinkAdditionId1, drinkAdditionId2);
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        //WHEN
        when(dishRepository.existsById(mainCourseId)).thenReturn(true);
        when(dishRepository.existsById(dessertId)).thenReturn(true);
        when(drinkRepository.existsById(drinkId)).thenReturn(true);
        when(drinkAdditionRepository.existsById(drinkAdditionId1)).thenReturn(true);
        when(drinkAdditionRepository.existsById(drinkAdditionId2)).thenReturn(true);
        when(orderMapper.fromOrderPostDto(orderPostDto)).thenReturn(order);
        orderServiceImpl.makeOrder(orderPostDto);
        //THEN
        verify(orderMapper, times(1)).fromOrderPostDto(orderPostDto);
        // Could use verify(orderRepository, times(1)).save(order);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        assertEquals(order, orderArgumentCaptor.getValue());
    }

    @Test
    void shouldNotCreateOrderWhenMainDishDoesNotExist() {
        //GIVEN
        Long mainCourseId = 1L;
        Long dessertId = 2L;
        Long drinkId = 1L;
        Long drinkAdditionId1 = 1L;
        Long drinkAdditionId2 = 2L;
        OrderPostDto orderPostDto = createOrderPostDto(mainCourseId, dessertId, drinkId, drinkAdditionId1, drinkAdditionId2);
        String errorCode = "dish.with.such.id.does.not.exist";
        String expectedErrorMessage = "Dish with such id does not exist";
        //WHEN
        when(dishRepository.existsById(mainCourseId)).thenReturn(false);
        when(messageService.getMessage(errorCode)).thenReturn(expectedErrorMessage);
        DishNotFoundException exception = assertThrows(DishNotFoundException.class, () -> orderServiceImpl.makeOrder(orderPostDto));
        //THEN
        verify(orderMapper, never()).fromOrderPostDto(orderPostDto);
        verify(orderRepository, never()).save(any());
        verify(dishRepository, times(1)).existsById(mainCourseId);
        verify(messageService, times(1)).getMessage(errorCode);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
    @Test
    void shouldNotCreateOrderWhenDessertDishDoesNotExist() {
        //GIVEN
        Long mainCourseId = 1L;
        Long dessertId = 2L;
        Long drinkId = 1L;
        Long drinkAdditionId1 = 1L;
        Long drinkAdditionId2 = 2L;
        OrderPostDto orderPostDto = createOrderPostDto(mainCourseId, dessertId, drinkId, drinkAdditionId1, drinkAdditionId2);
        String errorCode = "dish.with.such.id.does.not.exist";
        String expectedErrorMessage = "Dish with such id does not exist";
        //WHEN
        when(dishRepository.existsById(mainCourseId)).thenReturn(true);
        when(dishRepository.existsById(dessertId)).thenReturn(false);
        when(messageService.getMessage(errorCode)).thenReturn(expectedErrorMessage);
        DishNotFoundException exception = assertThrows(DishNotFoundException.class, () -> orderServiceImpl.makeOrder(orderPostDto));
        //THEN
        verify(orderMapper, never()).fromOrderPostDto(orderPostDto);
        verify(orderRepository, never()).save(any());
        verify(dishRepository, times(1)).existsById(dessertId);
        verify(messageService, times(1)).getMessage(errorCode);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
    @Test
    void shouldNotCreateOrderWhenDrinkDoesNotExist() {
        //GIVEN
        Long mainCourseId = 1L;
        Long dessertId = 2L;
        Long drinkId = 1L;
        Long drinkAdditionId1 = 1L;
        Long drinkAdditionId2 = 2L;
        OrderPostDto orderPostDto = createOrderPostDto(mainCourseId, dessertId, drinkId, drinkAdditionId1, drinkAdditionId2);
        String errorCode = "drink.with.such.id.does.not.exist";
        String expectedErrorMessage = "Drink with such id does not exist";
        //WHEN
        when(dishRepository.existsById(mainCourseId)).thenReturn(true);
        when(dishRepository.existsById(dessertId)).thenReturn(true);
        when(drinkRepository.existsById(drinkId)).thenReturn(false);
        when(messageService.getMessage(errorCode)).thenReturn(expectedErrorMessage);
        DrinkNotFoundException exception = assertThrows(DrinkNotFoundException.class, () -> orderServiceImpl.makeOrder(orderPostDto));
        //THEN
        verify(orderMapper, never()).fromOrderPostDto(orderPostDto);
        verify(orderRepository, never()).save(any());
        verify(drinkRepository, times(1)).existsById(drinkId);
        verify(messageService, times(1)).getMessage(errorCode);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
    @Test
    void shouldNotCreateOrderWhenDrinkAdditionDoesNotExist() {
        //GIVEN
        Long mainCourseId = 1L;
        Long dessertId = 2L;
        Long drinkId = 1L;
        Long drinkAdditionId1 = 1L;
        Long drinkAdditionId2 = 2L;
        OrderPostDto orderPostDto = createOrderPostDto(mainCourseId, dessertId, drinkId, drinkAdditionId1, drinkAdditionId2);
        String errorCode = "drink.addition.with.such.id.does.not.exist";
        String expectedErrorMessage = "Drink addition with such id does not exist";
        //WHEN
        when(dishRepository.existsById(mainCourseId)).thenReturn(true);
        when(dishRepository.existsById(dessertId)).thenReturn(true);
        when(drinkRepository.existsById(drinkId)).thenReturn(true);
        when(drinkAdditionRepository.existsById(drinkAdditionId1)).thenReturn(false);
        when(messageService.getMessage(errorCode)).thenReturn(expectedErrorMessage);
        DrinkAdditionNotFoundException exception = assertThrows(DrinkAdditionNotFoundException.class, () -> orderServiceImpl.makeOrder(orderPostDto));
        //THEN
        verify(orderMapper, never()).fromOrderPostDto(orderPostDto);
        verify(drinkAdditionRepository, never()).existsById(drinkAdditionId2);
        verify(orderRepository, never()).save(any());
        verify(drinkAdditionRepository, times(1)).existsById(drinkAdditionId1);
        verify(messageService, times(1)).getMessage(errorCode);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }


}
