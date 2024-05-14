package com.example.faceitassignment.core.mapper;

import com.example.faceitassignment.config.AbstractUnitServiceTest;
import com.example.faceitassignment.core.dto.OrderPostDto;
import com.example.faceitassignment.core.mapper.impl.OrderMapperImpl;
import com.example.faceitassignment.domain.entity.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderMapperImplTest extends AbstractUnitServiceTest {
    private OrderMapperImpl orderMapperImpl = new OrderMapperImpl();


    @Test
    void shouldMapOrderPostDtoToOrder() {
        //GIVEN
        OrderPostDto orderPostDto = createOrderPostDto(1L, 2L, 1L, 1L, 2L);
        Order expectedOrder = createOrder(1L, 2L, 1L, 1L, 2L);
        //WHEN
        Order actualOrder = orderMapperImpl.fromOrderPostDto(orderPostDto);
        //THEN
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void shouldNotMapOrderPostDtoToOrderIfIsNull() {
        //GIVEN
        //WHEN
        Order actualOrder = orderMapperImpl.fromOrderPostDto(null);
        //THEN
        assertNull(actualOrder);
    }

}
