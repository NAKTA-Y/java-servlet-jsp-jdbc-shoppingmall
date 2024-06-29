package com.nhnacademy.shoppingmall.check.order.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.util.DbUtils;
import com.nhnacademy.shoppingmall.entity.order.Order;
import com.nhnacademy.shoppingmall.entity.user.Address;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.order.OrderRepository;
import com.nhnacademy.shoppingmall.repository.order.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.user.AddressRepository;
import com.nhnacademy.shoppingmall.repository.user.UserRepository;
import com.nhnacademy.shoppingmall.repository.user.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.user.impl.UserRepositoryImpl;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class OrderRepositoryImplTest {
    OrderRepository orderRepository = new OrderRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();
    AddressRepository addressRepository = new AddressRepositoryImpl();

    User testUser;
    Order testOrder;
    Address testAddress;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testUser = User.builder()
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

        testAddress = Address.builder()
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

        testOrder = Order.builder()
                .orderID(1)
                .orderDate(LocalDateTime.now())
                .shipDate(null)
                .price(12345)
                .uniqueUserID(testUser.getUniqueUserID())
                .addressID(testAddress.getAddressID())
                .build();

        Connection connection = DbUtils.getDataSource().getConnection();

        String sql = "ALTER TABLE Orders AUTO_INCREMENT = 1";
        Statement statement = connection.createStatement();
        statement.execute(sql);

        sql = "ALTER TABLE Addresses AUTO_INCREMENT = 1";
        statement = connection.createStatement();
        statement.execute(sql);

        connection.close();

        userRepository.save(testUser);
        addressRepository.saveAddress(testAddress);
        orderRepository.save(testOrder);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("주문 저장시 id 값 2을 반환")
    @org.junit.jupiter.api.Order(1)
    void save() {
        // given
        Order newOrder = Order.builder()
                .orderID(2)
                .orderDate(LocalDateTime.now())
                .shipDate(null)
                .price(12345)
                .uniqueUserID(testUser.getUniqueUserID())
                .addressID(testAddress.getAddressID())
                .build();

        // when
        int id = orderRepository.save(newOrder);

        // then
        assertEquals(2, id);
    }

    @Test
    @DisplayName("Order 조회시 testOrder와 같음")
    @org.junit.jupiter.api.Order(2)
    void findByID() {
        // given
        // when
        Order findOrder = orderRepository.getByID(testOrder.getOrderID()).get();

        // then
        assertEquals(testOrder, findOrder);
    }

    @Test
    @DisplayName("Order 업데이트시 1을 반환")
    @org.junit.jupiter.api.Order(3)
    void update() {
        // given
        // when
        LocalDateTime now = LocalDateTime.now();
        int result = orderRepository.updateShipDate(testOrder.getOrderID(), now);

        // then
        Order updatedOrder = orderRepository.getByID(testOrder.getOrderID()).get();

        assertEquals(1, result);
    }

    @Test
    @DisplayName("유저에 대한 Order 페이지 가져오기")
    @org.junit.jupiter.api.Order(4)
    void pagination() {
        // given
        Order newOrder = Order.builder()
                .orderID(2)
                .orderDate(LocalDateTime.now())
                .shipDate(null)
                .price(12345)
                .uniqueUserID(testUser.getUniqueUserID())
                .addressID(testAddress.getAddressID())
                .build();

        orderRepository.save(newOrder);

        // when
        Page<Order> orderPage = orderRepository.getOrderPageByUserID(testUser.getUniqueUserID(), 1, 10);

        // then
        assertAll(
                () -> assertEquals(2, orderPage.getContent().size()),
                () -> assertEquals(2, orderPage.getTotalCount())
        );
    }
}
