package com.nhnacademy.shoppingmall.controller.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;
import com.nhnacademy.shoppingmall.entity.cart.ShoppingCart;
import com.nhnacademy.shoppingmall.entity.order.Order;
import com.nhnacademy.shoppingmall.entity.order.OrderDetail;
import com.nhnacademy.shoppingmall.entity.point.PointDetail;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.exception.order.OrderOutOfPriceException;
import com.nhnacademy.shoppingmall.repository.cart.impl.ShoppingCartRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.order.impl.OrderDetailRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.order.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.point.impl.PointDetailRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.service.cart.ShoppingCartService;
import com.nhnacademy.shoppingmall.service.cart.impl.ShoppingCartServiceImpl;
import com.nhnacademy.shoppingmall.service.order.OrderDetailService;
import com.nhnacademy.shoppingmall.service.order.OrderService;
import com.nhnacademy.shoppingmall.service.order.impl.OrderDetailServiceImpl;
import com.nhnacademy.shoppingmall.service.order.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.service.point.PointDetailService;
import com.nhnacademy.shoppingmall.service.point.impl.PointDetailServiceImpl;
import com.nhnacademy.shoppingmall.service.product.ProductService;
import com.nhnacademy.shoppingmall.service.product.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequestMapping(value = "/order/payment.do", method = RequestMapping.Method.POST)
public class OrderPaymentController implements BaseController {
    private static final String JSON_PARSING_EXCEPTION_MESSAGE = "요청된 데이터를 처리할 수 없습니다. 입력 형식을 확인해 주세요.";

    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());
    private final OrderDetailService orderDetailService = new OrderDetailServiceImpl(new OrderDetailRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final ShoppingCartService cartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());
    private final PointDetailService pointDetailService = new PointDetailServiceImpl(new PointDetailRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String cartJson = req.getParameter("carts");
            int totalPrice = Integer.parseInt(req.getParameter("total_price"));
            int addressId = Integer.parseInt(req.getParameter("address_id"));

            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            List<ShoppingCart> carts = objectMapper.readValue(cartJson, new TypeReference<>() {});

            int currentPoint = pointDetailService.getCurrentPointByUserID(user.getUniqueUserID());
            if (totalPrice > currentPoint)
                throw new OrderOutOfPriceException();

            Order newOrder = Order.builder()
                    .orderDate(LocalDateTime.now())
                    .shipDate(null)
                    .price(totalPrice)
                    .uniqueUserID(user.getUniqueUserID())
                    .addressID(addressId)
                    .build();

            int orderId = orderService.save(newOrder);

            for (ShoppingCart cart : carts) {
                Product product = productService.getProduct(cart.getProductID());

                OrderDetail orderDetail = OrderDetail.builder()
                        .orderID(orderId)
                        .productID(cart.getProductID())
                        .quantity(cart.getQuantity())
                        .unitPrice(product.getSale())
                        .build();

                orderDetailService.saveOrderDetail(orderDetail);
                cartService.deleteByUserIDAndProductID(user.getUniqueUserID(), cart.getProductID());
                productService.decreaseStock(product.getProductID(), cart.getQuantity());
                productService.increaseOrderCount(product.getProductID());
            }

            PointDetail pointDetail = PointDetail.builder()
                    .volume(-totalPrice)
                    .type(PointDetail.Type.POINT_USE)
                    .createdAt(LocalDateTime.now())
                    .uniqueUserID(user.getUniqueUserID())
                    .build();

            pointDetailService.savePointDetail(pointDetail);

            RequestChannel requestChannel = (RequestChannel) req.getServletContext().getAttribute("requestChannel");
            requestChannel.addRequest(new PointChannelRequest(user.getUniqueUserID(), (int) (totalPrice * 0.1)));

            session.setAttribute("cartItemCount", 0);

        } catch (JsonProcessingException e) {
            throw new WebApplicationException(HttpServletResponse.SC_BAD_REQUEST, JSON_PARSING_EXCEPTION_MESSAGE);
        } catch (InterruptedException e) {
            log.error("포인트 적립 오류 발생: " + e.getMessage());
        }

        return "redirect:/index.do";
    }
}
