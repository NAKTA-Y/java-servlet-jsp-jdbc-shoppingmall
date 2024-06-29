package com.nhnacademy.shoppingmall.controller.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;
import com.nhnacademy.shoppingmall.entity.cart.ShoppingCart;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.user.Address;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.cart.impl.ShoppingCartRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.user.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.service.cart.ShoppingCartService;
import com.nhnacademy.shoppingmall.service.cart.impl.ShoppingCartServiceImpl;
import com.nhnacademy.shoppingmall.service.product.ProductService;
import com.nhnacademy.shoppingmall.service.product.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.service.user.AddressService;
import com.nhnacademy.shoppingmall.service.user.impl.AddressServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping(value = "/order/summary.do", method = RequestMapping.Method.GET)
public class OrderController implements BaseController {
    private static final String JSON_PARSING_EXCEPTION_MESSAGE = "요청된 데이터를 처리할 수 없습니다. 입력 형식을 확인해 주세요.";

    private final ShoppingCartService cartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        List<ShoppingCart> shoppingCarts = cartService.getShoppingCartByUserID(user.getUniqueUserID());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String cartsJson = objectMapper.writeValueAsString(shoppingCarts);

            List<Integer> productIDs = shoppingCarts.stream().map(ShoppingCart::getProductID).collect(Collectors.toList());
            List<Product> products = productService.getProductsByIDList(productIDs);

            Map<Integer, Product> productMap = new HashMap<>();
            for (Product product : products) productMap.put(product.getProductID(), product);

            List<Address> addresses = addressService.getAddressesByUserID(user.getUniqueUserID());
            Address defaultAddress = addresses.stream().filter(Address::isDefaultAddress).findAny().orElse(null);

            long totalPrice = shoppingCarts.stream()
                    .mapToLong(cart -> (long) productMap.get(cart.getProductID()).getPrice() * cart.getQuantity())
                    .sum();

            long totalSale = shoppingCarts.stream()
                    .mapToLong(cart -> (long) productMap.get(cart.getProductID()).getSale() * cart.getQuantity())
                    .sum();

            req.setAttribute("cartsJson", cartsJson);
            req.setAttribute("shoppingCarts", shoppingCarts);
            req.setAttribute("productMap", productMap);
            req.setAttribute("totalPrice", totalPrice);
            req.setAttribute("totalSale", totalSale);
            req.setAttribute("addresses", addresses);
            req.setAttribute("defaultAddress", defaultAddress);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new WebApplicationException(HttpServletResponse.SC_BAD_REQUEST, JSON_PARSING_EXCEPTION_MESSAGE);
        }

        return "/shop/order/order_summary";
    }
}
