package com.example.faceitassignment.web.controller;


import com.example.faceitassignment.core.dto.OrderPostDto;
import com.example.faceitassignment.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/secure/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<?> makeOrder(@RequestBody OrderPostDto orderPostDto){
        orderService.makeOrder(orderPostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
