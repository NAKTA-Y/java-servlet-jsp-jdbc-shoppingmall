package com.nhnacademy.shoppingmall.service.order.impl;

import com.nhnacademy.shoppingmall.repository.order.OrderDetailRepository;
import com.nhnacademy.shoppingmall.service.order.OrderDetailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

class OrderDetailServiceImplTest {
    OrderDetailRepository orderDetailRepository = Mockito.mock(OrderDetailRepository.class);
    OrderDetailService orderDetailService = new OrderDetailServiceImpl(orderDetailRepository);

    @Test
    @DisplayName("save order detail")
    void save() {
        Mockito.when(orderDetailRepository.saveOrderDetail(any())).thenReturn(1);
        orderDetailService.saveOrderDetail(any());
        Mockito.verify(orderDetailRepository, Mockito.times(1)).saveOrderDetail(any());
    }

    @Test
    @DisplayName("get products by order id")
    void get_products_by_order_id() {
        Mockito.when(orderDetailRepository.getProductsByOrderID(anyInt())).thenReturn(new ArrayList<>());
        orderDetailService.getProductsByOrderID(anyInt());
        Mockito.verify(orderDetailRepository, Mockito.times(1)).getProductsByOrderID(anyInt());
    }

    @Test
    @DisplayName("get order details by order id")
    void get_order_details_by_order_id() {
        Mockito.when(orderDetailRepository.getOrderDetailsByOrderID(anyInt())).thenReturn(new ArrayList<>());
        orderDetailService.getOrderDetailsByOrderID(anyInt());
        Mockito.verify(orderDetailRepository, Mockito.times(1)).getOrderDetailsByOrderID(anyInt());
    }
}