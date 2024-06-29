package com.nhnacademy.shoppingmall.service.cart.impl;

import com.nhnacademy.shoppingmall.entity.cart.ShoppingCart;
import com.nhnacademy.shoppingmall.repository.cart.ShoppingCartRepository;
import com.nhnacademy.shoppingmall.service.cart.ShoppingCartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

class ShoppingCartServiceImplTest {
    ShoppingCartRepository shoppingCartRepository = Mockito.mock(ShoppingCartRepository.class);
    ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(shoppingCartRepository);

    ShoppingCart testShoppingCart = ShoppingCart.builder()
            .uniqueUserID("testUserID")
            .productID(1)
            .quantity(1)
            .createdAt(LocalDateTime.now())
            .build();

    @Test
    @DisplayName("save shoppingcart")
    void saveReview() {
        Mockito.when(shoppingCartRepository.save(any())).thenReturn(1);
        Mockito.when(shoppingCartRepository.isExist(any(), anyInt())).thenReturn(true);

        shoppingCartService.saveShoppingCart(testShoppingCart);

        Mockito.verify(shoppingCartRepository, Mockito.times(1)).isExist(any(), anyInt());
    }

    @Test
    @DisplayName("delete shoppingcart")
    void deleteReview() {
        Mockito.when(shoppingCartRepository.deleteByUserIDAndProductID(any(), anyInt())).thenReturn(1);
        Mockito.when(shoppingCartRepository.isExist(any(), anyInt())).thenReturn(true);
        shoppingCartService.deleteByUserIDAndProductID("testUserID", 1);
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).deleteByUserIDAndProductID(any(), anyInt());
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).isExist(any(), anyInt());
    }

    @Test
    @DisplayName("update shoppingcart quantity")
    void updateQuantity() {
        Mockito.when(shoppingCartRepository.updateQuantity(any(), anyInt(), anyInt())).thenReturn(1);
        Mockito.when(shoppingCartRepository.isExist(any(), anyInt())).thenReturn(true);
        shoppingCartService.updateQuantity("testUserID", 1, 1);
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).updateQuantity(any(), anyInt(), anyInt());
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).isExist(any(), anyInt());
    }

    @Test
    @DisplayName("get shoppingcart")
    void getProducts() {
        List<ShoppingCart> shoppingCart = new ArrayList<>();
        Mockito.when(shoppingCartRepository.getShoppingCartByUserID(any())).thenReturn(shoppingCart);
        shoppingCartService.getShoppingCartByUserID("testUserID");
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).getShoppingCartByUserID(any());
    }
}