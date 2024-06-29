package com.nhnacademy.shoppingmall.entity.order;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
public class Order {
    private int orderID;
    private LocalDateTime orderDate;
    private LocalDateTime shipDate;
    private int price;
    private String uniqueUserID;
    private int addressID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID && price == order.price && addressID == order.addressID && Objects.equals(orderDate, order.orderDate) && Objects.equals(shipDate, order.shipDate) && Objects.equals(uniqueUserID, order.uniqueUserID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, orderDate, shipDate, price, uniqueUserID, addressID);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", orderDate=" + orderDate +
                ", shipDate=" + shipDate +
                ", price=" + price +
                ", uniqueUserID='" + uniqueUserID + '\'' +
                ", addressID=" + addressID +
                '}';
    }
}
