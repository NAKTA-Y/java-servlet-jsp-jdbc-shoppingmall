package com.nhnacademy.shoppingmall.service.cart.impl;

import com.nhnacademy.shoppingmall.entity.cart.ShoppingCart;
import com.nhnacademy.shoppingmall.exception.cart.CartNotFoundException;
import com.nhnacademy.shoppingmall.exception.cart.CartNoEffectException;
import com.nhnacademy.shoppingmall.repository.cart.ShoppingCartRepository;
import com.nhnacademy.shoppingmall.service.cart.ShoppingCartService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ShoppingCartServiceImpl implements ShoppingCartService {
    private static final String CART_SAVE_EXCEPTION_MESSAGE = "쇼핑 카트에 항목을 추가하지 못했습니다.";
    private static final String CART_NOT_FOUND_EXCEPTION_MESSAGE = "쇼핑 카트에 항목을 찾지 못했습니다.";
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public void saveShoppingCart(ShoppingCart shoppingCart) {
        int result = shoppingCartRepository.save(shoppingCart);
        if (result < 1)
            throw new CartNoEffectException(HttpServletResponse.SC_NO_CONTENT, CART_SAVE_EXCEPTION_MESSAGE);
    }

    @Override
    public List<ShoppingCart> getShoppingCartByUserID(String uniqueUserID) {
        return shoppingCartRepository.getShoppingCartByUserID(uniqueUserID);
    }

    @Override
    public void deleteByUserIDAndProductID(String uniqueUserID, int productID) {
        if (!shoppingCartRepository.isExist(uniqueUserID, productID))
            throw new CartNotFoundException(HttpServletResponse.SC_NOT_FOUND, CART_NOT_FOUND_EXCEPTION_MESSAGE);

        int result = shoppingCartRepository.deleteByUserIDAndProductID(uniqueUserID, productID);
        if (result < 1)
            throw new CartNoEffectException(HttpServletResponse.SC_NO_CONTENT, CART_SAVE_EXCEPTION_MESSAGE);
    }

    @Override
    public void updateQuantity(String uniqueUserID, int productID, int quantity) {
        try {
            if (!shoppingCartRepository.isExist(uniqueUserID, productID))
                throw new CartNotFoundException(HttpServletResponse.SC_NOT_FOUND, CART_NOT_FOUND_EXCEPTION_MESSAGE);

            int result = shoppingCartRepository.updateQuantity(uniqueUserID, productID, quantity);
            if (result < 1)
                throw new CartNoEffectException(HttpServletResponse.SC_NO_CONTENT, CART_SAVE_EXCEPTION_MESSAGE);
        } catch (RuntimeException e) {
            // 예외 처리
        }
    }

    @Override
    public ShoppingCart getCartByUserIDAndProductID(String uniqueUserID, int productID) {
        Optional<ShoppingCart> cartOptional = shoppingCartRepository.getCartByUserIDAndProductID(uniqueUserID, productID);
        return cartOptional.orElse(null);
    }

    @Override
    public int getCountAllByUserID(String uniqueUserID) {
        return shoppingCartRepository.countAllByUserID(uniqueUserID);
    }
}
