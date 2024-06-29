package com.nhnacademy.shoppingmall.service.order.impl;

import com.nhnacademy.shoppingmall.entity.order.OrderDetail;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.exception.order.OrderNoEffectException;
import com.nhnacademy.shoppingmall.repository.order.OrderDetailRepository;
import com.nhnacademy.shoppingmall.service.order.OrderDetailService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OrderDetailServiceImpl implements OrderDetailService {
    private static final String ORDER_DETAIL_SAVE_EXCEPTION_MESSAGE = "주문 항목을 추가하지 못했습니다.";
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {
        int result = orderDetailRepository.saveOrderDetail(orderDetail);
        if (result < 1) throw new OrderNoEffectException(HttpServletResponse.SC_NO_CONTENT, ORDER_DETAIL_SAVE_EXCEPTION_MESSAGE);
    }

    @Override
    public List<Product> getProductsByOrderID(int orderID) {
        return orderDetailRepository.getProductsByOrderID(orderID);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderID(int orderID) {
        return orderDetailRepository.getOrderDetailsByOrderID(orderID);
    }
}
