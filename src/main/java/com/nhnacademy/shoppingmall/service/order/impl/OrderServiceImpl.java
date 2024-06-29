package com.nhnacademy.shoppingmall.service.order.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.order.Order;
import com.nhnacademy.shoppingmall.exception.order.OrderNoEffectException;
import com.nhnacademy.shoppingmall.exception.order.OrderNotFoundException;
import com.nhnacademy.shoppingmall.repository.order.OrderRepository;
import com.nhnacademy.shoppingmall.service.order.OrderService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private static final String ORDER_SAVE_EXCEPTION_MESSAGE = "주문을 수행하지 못했습니다.";
    private static final String ORDER_NOT_FOUND_EXCEPTION_MESSAGE = "주문을 찾지 못했습니다.";

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public int save(Order order) {
        int pk = orderRepository.save(order);
        if (pk < 1) throw new OrderNoEffectException(HttpServletResponse.SC_NO_CONTENT, ORDER_SAVE_EXCEPTION_MESSAGE);
        return pk;
    }

    @Override
    public Order getByID(int orderID) {
        Optional<Order> orderOptional = orderRepository.getByID(orderID);
        if (orderOptional.isEmpty()) throw new OrderNotFoundException(HttpServletResponse.SC_NOT_FOUND, ORDER_NOT_FOUND_EXCEPTION_MESSAGE);
        return orderOptional.get();
    }

    @Override
    public void updateShipDate(int orderID, LocalDateTime date) {
        int result = orderRepository.updateShipDate(orderID, date);
        if (result < 1) throw new OrderNoEffectException(HttpServletResponse.SC_NO_CONTENT, ORDER_SAVE_EXCEPTION_MESSAGE);
    }

    @Override
    public Page<Order> getOrderPageByUserID(String uniqueUserID, int page, int pageSize) {
        return orderRepository.getOrderPageByUserID(uniqueUserID, page, pageSize);
    }
}
