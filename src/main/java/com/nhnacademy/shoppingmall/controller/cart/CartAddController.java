package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.InvalidParameterException;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.PageNotFoundException;
import com.nhnacademy.shoppingmall.entity.cart.ShoppingCart;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.exception.cart.CartOutOfStockException;
import com.nhnacademy.shoppingmall.repository.cart.impl.ShoppingCartRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.service.cart.ShoppingCartService;
import com.nhnacademy.shoppingmall.service.cart.impl.ShoppingCartServiceImpl;
import com.nhnacademy.shoppingmall.service.product.ProductService;
import com.nhnacademy.shoppingmall.service.product.impl.ProductServiceImpl;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Objects;

@RequestMapping(value = "/cart/add.do", method = RequestMapping.Method.POST)
public class CartAddController implements BaseController {
    private final ShoppingCartService cartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            int productId = Integer.parseInt(req.getParameter("product_id"));
            int amount = Integer.parseInt(req.getParameter("amount"));

            if (ObjectUtils.anyNull(productId, amount))
                throw new PageNotFoundException();

            Product product = productService.getProduct(productId);
            ShoppingCart cart = cartService.getCartByUserIDAndProductID(user.getUniqueUserID(), productId);

            if (Objects.nonNull(cart)) {
                if (cart.getQuantity() + amount > product.getStock())
                    throw new CartOutOfStockException();

                cartService.updateQuantity(user.getUniqueUserID(), productId, cart.getQuantity() + amount);
            } else {
                ShoppingCart shoppingCart = ShoppingCart.builder()
                        .uniqueUserID(user.getUniqueUserID())
                        .productID(productId)
                        .quantity(amount)
                        .createdAt(LocalDateTime.now())
                        .build();

                cartService.saveShoppingCart(shoppingCart);
            }

            int cartItemCount = cartService.getCountAllByUserID(user.getUniqueUserID());
            session.setAttribute("cartItemCount", cartItemCount);

            return "redirect:/product/detail.do?product_id=" + productId;
        } catch (NumberFormatException e) {
            throw InvalidParameterException.numberFormatException();
        }
    }
}
