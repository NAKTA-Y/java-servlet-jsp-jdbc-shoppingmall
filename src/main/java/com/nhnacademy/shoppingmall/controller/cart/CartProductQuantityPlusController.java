package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.InvalidParameterException;
import com.nhnacademy.shoppingmall.entity.cart.ShoppingCart;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.cart.impl.ShoppingCartRepositoryImpl;
import com.nhnacademy.shoppingmall.service.cart.ShoppingCartService;
import com.nhnacademy.shoppingmall.service.cart.impl.ShoppingCartServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping(value = "/cart/plus_quantity.do", method = RequestMapping.Method.GET)
public class CartProductQuantityPlusController implements BaseController {
    private final ShoppingCartService cartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            int productId = Integer.parseInt(req.getParameter("product_id"));
            int stock = Integer.parseInt(req.getParameter("stock"));

            ShoppingCart cart = cartService.getCartByUserIDAndProductID(user.getUniqueUserID(), productId);

            if (cart.getQuantity() < stock)
                cartService.updateQuantity(user.getUniqueUserID(), productId, cart.getQuantity() + 1);

        } catch (NumberFormatException e) {
            throw InvalidParameterException.numberFormatException();
        }

        return "redirect:/cart/list.do";
    }
}
