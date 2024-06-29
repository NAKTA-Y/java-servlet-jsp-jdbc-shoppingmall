package com.nhnacademy.shoppingmall.service.order;

import com.nhnacademy.shoppingmall.entity.order.OrderDetail;
import com.nhnacademy.shoppingmall.entity.product.Product;

import java.util.List;

public interface OrderDetailService {
    void saveOrderDetail(OrderDetail orderDetail);
    List<Product> getProductsByOrderID(int orderID);
    List<OrderDetail> getOrderDetailsByOrderID(int orderID);
}
