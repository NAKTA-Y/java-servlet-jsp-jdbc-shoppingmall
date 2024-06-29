package com.nhnacademy.shoppingmall.service.cart;

import com.nhnacademy.shoppingmall.entity.cart.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void saveShoppingCart(ShoppingCart shoppingCart);
    List<ShoppingCart> getShoppingCartByUserID(String uniqueUserID);
    void deleteByUserIDAndProductID(String uniqueUserID, int productID);
    void updateQuantity(String uniqueUserID, int productID, int quantity);
    ShoppingCart getCartByUserIDAndProductID(String uniqueUserID, int productID);

    int getCountAllByUserID(String uniqueUserID);
}
