package com.nhnacademy.shoppingmall.repository.order;

import com.nhnacademy.shoppingmall.entity.order.OrderDetail;
import com.nhnacademy.shoppingmall.entity.product.Product;

import java.util.List;

public interface OrderDetailRepository {
    int saveOrderDetail(OrderDetail orderDetail);
    List<Product> getProductsByOrderID(int orderID);
    List<OrderDetail> getOrderDetailsByOrderID(int orderID);
}
