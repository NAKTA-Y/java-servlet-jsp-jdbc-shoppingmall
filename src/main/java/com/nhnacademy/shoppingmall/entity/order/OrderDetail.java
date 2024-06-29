package com.nhnacademy.shoppingmall.entity.order;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class OrderDetail {
    private int orderID;
    private int productID;
    private int quantity;
    private int unitPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return orderID == that.orderID && productID == that.productID && quantity == that.quantity && unitPrice == that.unitPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, productID, quantity, unitPrice);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderID=" + orderID +
                ", productID=" + productID +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
