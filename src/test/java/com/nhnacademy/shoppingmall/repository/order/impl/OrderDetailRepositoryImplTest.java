package com.nhnacademy.shoppingmall.repository.order.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.util.DbUtils;
import com.nhnacademy.shoppingmall.entity.order.Order;
import com.nhnacademy.shoppingmall.entity.order.OrderDetail;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.user.Address;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.order.OrderDetailRepository;
import com.nhnacademy.shoppingmall.repository.order.OrderRepository;
import com.nhnacademy.shoppingmall.repository.product.ProductRepository;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.user.AddressRepository;
import com.nhnacademy.shoppingmall.repository.user.UserRepository;
import com.nhnacademy.shoppingmall.repository.user.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.user.impl.UserRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDetailRepositoryImplTest {
    UserRepository userRepository = new UserRepositoryImpl();
    AddressRepository addressRepository = new AddressRepositoryImpl();
    OrderRepository orderRepository = new OrderRepositoryImpl();
    ProductRepository productRepository = new ProductRepositoryImpl();
    OrderDetailRepository orderDetailRepository = new OrderDetailRepositoryImpl();

    User testUser = User.builder()
            .uniqueUserID("nhnacademy-unique-id")
            .userID("nhnacademy-test-user")
            .userName("nhn아카데미")
            .userPassword("nhnacademy-test-password")
            .userBirth("19990101")
            .userTelephone("010-1234-1234")
            .userAuth(User.Auth.ROLE_USER)
            .createdAt(LocalDateTime.now())
            .state(User.State.ACTIVE)
            .build();

    Address testAddress = Address.builder()
            .addressID(1)
            .address("testAddress")
            .addressDetail("testAddressDetail")
            .zipcode("123456")
            .name("testName")
            .telephone("010-1234-1234")
            .request("testRequest")
            .defaultAddress(false)
            .uniqueUserID(testUser.getUniqueUserID())
            .build();

    Order testOrder = Order.builder()
            .orderID(1)
            .orderDate(LocalDateTime.now())
            .shipDate(null)
            .price(12345)
            .uniqueUserID(testUser.getUniqueUserID())
            .addressID(testAddress.getAddressID())
            .build();

    Product testProduct = Product.builder()
            .productID(1)
            .modelNumber("Number")
            .modelName("testModelName")
            .banner("banner")
            .thumbnail("thumbnail")
            .price(100)
            .sale(50)
            .description("description")
            .stock(1)
            .viewCount(1)
            .orderCount(1)
            .brand("brand")
            .state(Product.State.ACTIVE)
            .createdAt(LocalDateTime.now())
            .build();

    OrderDetail testOrderDetail = OrderDetail.builder()
            .orderID(testOrder.getOrderID())
            .productID(testProduct.getProductID())
            .quantity(1)
            .unitPrice(100)
            .build();

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DbUtils.getDataSource().getConnection();

        String sql = "ALTER TABLE Orders AUTO_INCREMENT = 1";
        Statement statement = connection.createStatement();
        statement.execute(sql);

        sql = "ALTER TABLE Addresses AUTO_INCREMENT = 1";
        statement = connection.createStatement();
        statement.execute(sql);

        sql = "ALTER TABLE Products AUTO_INCREMENT = 1";
        statement = connection.createStatement();
        statement.execute(sql);

        sql = "ALTER TABLE OrderDetails AUTO_INCREMENT = 1";
        statement = connection.createStatement();
        statement.execute(sql);

        connection.close();

        DbConnectionThreadLocal.initialize();

        userRepository.save(testUser);
        productRepository.save(testProduct);
        addressRepository.saveAddress(testAddress);
        orderRepository.save(testOrder);
        orderDetailRepository.saveOrderDetail(testOrderDetail);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("주문 내역 기록시 1 결과값 반환")
    @org.junit.jupiter.api.Order(1)
    void save() {
        // given
        Product newProduct = Product.builder()
                .productID(2)
                .modelNumber("Number")
                .modelName("testModelName")
                .banner("banner")
                .thumbnail("thumbnail")
                .price(100)
                .sale(50)
                .description("description")
                .stock(1)
                .viewCount(1)
                .orderCount(1)
                .brand("brand")
                .state(Product.State.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        OrderDetail newOrderDetail = OrderDetail.builder()
                .orderID(testOrder.getOrderID())
                .productID(newProduct.getProductID())
                .quantity(1)
                .unitPrice(100)
                .build();

        productRepository.save(newProduct);

        // when
        int result = orderDetailRepository.saveOrderDetail(newOrderDetail);

        // then
        assertEquals(1, result);
    }

    @Test
    @DisplayName("주문 ID로 상품들 가져오기")
    @org.junit.jupiter.api.Order(2)
    void get_products_by_order_id() {
        // given
        Product newProduct = Product.builder()
                .productID(2)
                .modelNumber("Number")
                .modelName("testModelName")
                .banner("banner")
                .thumbnail("thumbnail")
                .price(100)
                .sale(50)
                .description("description")
                .stock(1)
                .viewCount(1)
                .orderCount(1)
                .brand("brand")
                .state(Product.State.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        OrderDetail newOrderDetail = OrderDetail.builder()
                .orderID(testOrder.getOrderID())
                .productID(newProduct.getProductID())
                .quantity(1)
                .unitPrice(100)
                .build();

        productRepository.save(newProduct);
        orderDetailRepository.saveOrderDetail(newOrderDetail);

        // when
        List<Product> products = orderDetailRepository.getProductsByOrderID(testOrder.getOrderID());

        // then
        assertEquals(2, products.size());
    }

    @Test
    @DisplayName("주문 ID로 주문 내역들 가져오기")
    @org.junit.jupiter.api.Order(3)
    void get_order_details_by_order_id() {
        // given
        Product newProduct = Product.builder()
                .productID(2)
                .modelNumber("Number")
                .modelName("testModelName")
                .banner("banner")
                .thumbnail("thumbnail")
                .price(100)
                .sale(50)
                .description("description")
                .stock(1)
                .viewCount(1)
                .orderCount(1)
                .brand("brand")
                .state(Product.State.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        OrderDetail newOrderDetail = OrderDetail.builder()
                .orderID(testOrder.getOrderID())
                .productID(newProduct.getProductID())
                .quantity(1)
                .unitPrice(100)
                .build();

        productRepository.save(newProduct);
        orderDetailRepository.saveOrderDetail(newOrderDetail);

        // when
        List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetailsByOrderID(testOrder.getOrderID());

        // then
        assertEquals(2, orderDetails.size());
    }
}