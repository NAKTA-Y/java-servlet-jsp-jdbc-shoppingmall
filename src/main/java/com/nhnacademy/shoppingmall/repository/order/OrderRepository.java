package com.nhnacademy.shoppingmall.repository.order;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.order.Order;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository {
    int save(Order order);
    Optional<Order> getByID(int id);
    int updateShipDate(int orderID, LocalDateTime date);
    Page<Order> getOrderPageByUserID(String uniqueUserID, int page, int pageSize);
}
