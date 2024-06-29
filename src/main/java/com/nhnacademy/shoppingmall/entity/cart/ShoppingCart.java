package com.nhnacademy.shoppingmall.entity.cart;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShoppingCart {
    private String uniqueUserID;
    private int productID;
    private int quantity;
    private LocalDateTime createdAt;

    @Builder
    public ShoppingCart(String uniqueUserID, int productID, int quantity, LocalDateTime createdAt) {
        this.uniqueUserID = uniqueUserID;
        this.productID = productID;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return productID == that.productID && quantity == that.quantity && Objects.equals(uniqueUserID, that.uniqueUserID) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueUserID, productID, quantity, createdAt);
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "uniqueUserID='" + uniqueUserID + '\'' +
                ", productID=" + productID +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                '}';
    }
}
