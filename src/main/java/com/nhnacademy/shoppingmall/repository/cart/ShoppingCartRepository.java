package com.nhnacademy.shoppingmall.repository.cart;

import com.nhnacademy.shoppingmall.entity.cart.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository {
    int save(ShoppingCart shoppingCart);
    int deleteByUserIDAndProductID(String uniqueUserID, int productID);
    int updateQuantity(String uniqueUserID, int productID, int quantity);
    List<ShoppingCart> getShoppingCartByUserID(String uniqueUserID);
    Optional<ShoppingCart> getCartByUserIDAndProductID(String uniqueUserID, int productID);
    boolean isExist(String uniqueUserID, int productID);
    int countAllByUserID(String uniqueUserID);
}