package com.nhnacademy.shoppingmall.service.order.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.order.Order;
import com.nhnacademy.shoppingmall.repository.order.OrderRepository;
import com.nhnacademy.shoppingmall.service.order.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

class OrderServiceImplTest {
    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    OrderService orderService = new OrderServiceImpl(orderRepository);

    @Test
    @DisplayName("save order")
    void save_order() {
        Mockito.when(orderRepository.save(any())).thenReturn(1);
        orderService.save(Order.builder().build());
        Mockito.verify(orderRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("get order")
    void get_order() {
        Mockito.when(orderRepository.getByID(anyInt())).thenReturn(any());
        Assertions.assertThrows(RuntimeException.class, () -> orderService.getByID(1));
        Mockito.verify(orderRepository, Mockito.times(1)).getByID(anyInt());
    }

    @Test
    @DisplayName("update")
    void update() {
        Mockito.when(orderRepository.updateShipDate(anyInt(), any())).thenReturn(1);
        orderService.updateShipDate(1, LocalDateTime.now());
        Mockito.verify(orderRepository, Mockito.times(1)).updateShipDate(anyInt(), any());
    }

    @Test
    @DisplayName("pagination")
    void pagination() {
        List<Order> orders = new ArrayList<>();
        Page<Order> orderPage = new Page<>(orders, 0);

        Mockito.when(orderRepository.getOrderPageByUserID(anyString(), anyInt(), anyInt())).thenReturn(orderPage);
        orderService.getOrderPageByUserID("any", 1, 10);
        Mockito.verify(orderRepository, Mockito.times(1)).getOrderPageByUserID(anyString(), anyInt(), anyInt());
    }
}