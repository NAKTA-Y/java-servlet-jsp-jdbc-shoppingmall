package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.InvalidParameterException;
import com.nhnacademy.shoppingmall.entity.order.Order;
import com.nhnacademy.shoppingmall.entity.order.OrderDetail;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.user.Address;
import com.nhnacademy.shoppingmall.repository.order.impl.OrderDetailRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.order.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.user.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.service.order.OrderDetailService;
import com.nhnacademy.shoppingmall.service.order.OrderService;
import com.nhnacademy.shoppingmall.service.order.impl.OrderDetailServiceImpl;
import com.nhnacademy.shoppingmall.service.order.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.service.user.AddressService;
import com.nhnacademy.shoppingmall.service.user.impl.AddressServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/mypage/order/detail.do", method = RequestMapping.Method.GET)
public class OrderDetailController implements BaseController {
    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());
    private final OrderDetailService orderDetailService = new OrderDetailServiceImpl(new OrderDetailRepositoryImpl());
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int orderId = Integer.parseInt(req.getParameter("order_id"));

            Order order = orderService.getByID(orderId);
            Address address = addressService.getAddressByID(order.getAddressID());
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderID(orderId);

            Map<Integer, Product> productMap = new HashMap<>();
            List<Product> products = orderDetailService.getProductsByOrderID(orderId);
            for (Product product : products) productMap.put(product.getProductID(), product);

            req.setAttribute("order", order);
            req.setAttribute("address", address);
            req.setAttribute("orderDetails", orderDetails);
            req.setAttribute("productMap", productMap);

        } catch (NumberFormatException e) {
            throw InvalidParameterException.numberFormatException();
        }

        return "/shop/mypage/order_detail";
    }
}
