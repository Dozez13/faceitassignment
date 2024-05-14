package com.example.faceitassignment.web.controller;

import com.example.faceitassignment.config.AbstractUnitWebTest;
import com.example.faceitassignment.core.dto.OrderPostDto;
import com.example.faceitassignment.core.exception.DishNotFoundException;
import com.example.faceitassignment.core.exception.DrinkAdditionNotFoundException;
import com.example.faceitassignment.core.exception.DrinkNotFoundException;
import com.example.faceitassignment.core.exception.response.ErrorResponse;
import com.example.faceitassignment.datagenerator.dto.OrderPostDtoGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest extends AbstractUnitWebTest {
    @Autowired
    private OrderPostDtoGenerator orderPostDtoGenerator;

    @Test
    void shouldOrderPostReturnCreateIfBodyIsValid() throws Exception {
        //GIVEN
        OrderPostDto orderPostDto = orderPostDtoGenerator.orderPostDto(1L, 2L, 1L, 1L, 2L);
        ArgumentCaptor<OrderPostDto> orderCaptor = ArgumentCaptor.forClass(OrderPostDto.class);
        //WHEN
        mockMvc.perform(post("/api/v1/secure/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderPostDto)))
                .andExpect(status().isCreated());
        //THEN
        // Could use verify(orderService, times(1)).makeOrder(orderPostDto);
        verify(orderService).makeOrder(orderCaptor.capture());
        assertEquals(orderPostDto, orderCaptor.getValue());
    }

    @ParameterizedTest
    @MethodSource(value = {"postOrderErrors"})
    void shouldOrderPostThrowExceptionIfProductNotFound(OrderPostDto orderPostDto, RuntimeException exception, ErrorResponse<?> expectedErrorResponse) throws Exception {
        //GIVEN
        //WHEN
        doThrow(exception).when(orderService).makeOrder(orderPostDto);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/secure/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderPostDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
        ErrorResponse<?> actualErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ErrorResponse<?>>() {
        });
        //THEN
        assertEquals(expectedErrorResponse, actualErrorResponse);
    }


    private static Stream<Arguments> postOrderErrors() {
        return Stream.of(
                Arguments.of(
                        passedOrderPostDto(-1L, 2L, 1L, 1L, 2L),
                        new DishNotFoundException("Dish with such id does not exist"),
                        expectedErrorResponse(HttpStatus.BAD_REQUEST, "Dish with such id does not exist")),
                Arguments.of(
                        passedOrderPostDto(1L, 2L, -1L, 1L, 2L),
                        new DrinkNotFoundException("Drink with such id does not exist"),
                        expectedErrorResponse(HttpStatus.BAD_REQUEST, "Drink with such id does not exist")),
                Arguments.of(
                        passedOrderPostDto(1L, 2L, 1L, -1L, 2L),
                        new DrinkAdditionNotFoundException("Drink addition with such id does not exist"),
                        expectedErrorResponse(HttpStatus.BAD_REQUEST, "Drink addition with such id does not exist"))

        );
    }

    private static ErrorResponse<?> expectedErrorResponse(HttpStatus status, String title) {
        return new ErrorResponse<>(status.value(), title, null);
    }

    public static OrderPostDto passedOrderPostDto(Long mainCourseId, Long dessertId, Long drinkId, Long... drinkAdditionIds) {
        OrderPostDto orderPostDto = new OrderPostDto();
        orderPostDto.setMainCourseId(mainCourseId);
        orderPostDto.setDessertId(dessertId);
        orderPostDto.setDrinkId(drinkId);
        orderPostDto.setDrinkAdditionsIds(Arrays.asList(drinkAdditionIds));
        return orderPostDto;
    }
}
