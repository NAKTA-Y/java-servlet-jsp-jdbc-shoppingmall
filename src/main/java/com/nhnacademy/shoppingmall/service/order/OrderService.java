package com.nhnacademy.shoppingmall.service.order;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.order.Order;

import java.time.LocalDateTime;

public interface OrderService {
    int save(Order order);
    Order getByID(int orderID);
    void updateShipDate(int orderID, LocalDateTime date);
    Page<Order> getOrderPageByUserID(String uniqueUserID, int page, int pageSize);
}
