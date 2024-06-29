package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.entity.cart.ShoppingCart;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.cart.impl.ShoppingCartRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.service.cart.ShoppingCartService;
import com.nhnacademy.shoppingmall.service.cart.impl.ShoppingCartServiceImpl;
import com.nhnacademy.shoppingmall.service.product.ProductService;
import com.nhnacademy.shoppingmall.service.product.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping(value = "/cart/list.do", method = RequestMapping.Method.GET)
public class CartListController implements BaseController {
    private final ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        List<ShoppingCart> shoppingCarts = shoppingCartService.getShoppingCartByUserID(user.getUniqueUserID());

        List<Integer> productIDs = shoppingCarts.stream().map(ShoppingCart::getProductID).collect(Collectors.toList());
        List<Product> products = productService.getProductsByIDList(productIDs);

        Map<Integer, Product> productMap = new HashMap<>();
        for (Product product : products) productMap.put(product.getProductID(), product);

        long totalPrice = shoppingCarts.stream()
                .mapToLong(cart -> (long) productMap.get(cart.getProductID()).getPrice() * cart.getQuantity())
                .sum();

        long totalSale = shoppingCarts.stream()
                .mapToLong(cart -> (long) productMap.get(cart.getProductID()).getSale() * cart.getQuantity())
                .sum();

        req.setAttribute("shoppingCarts", shoppingCarts);
        req.setAttribute("productMap", productMap);
        req.setAttribute("totalPrice", totalPrice);
        req.setAttribute("totalSale", totalSale);

        return "/shop/cart/cart_list";
    }
}
