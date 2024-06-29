package com.nhnacademy.shoppingmall.repository.point.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.util.DbUtils;
import com.nhnacademy.shoppingmall.entity.order.Order;
import com.nhnacademy.shoppingmall.entity.point.PointDetail;
import com.nhnacademy.shoppingmall.entity.user.Address;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.order.OrderRepository;
import com.nhnacademy.shoppingmall.repository.order.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.point.PointDetailRepository;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PointDetailRepositoryImplTest {
    UserRepository userRepository = new UserRepositoryImpl();
    AddressRepository addressRepository = new AddressRepositoryImpl();
    OrderRepository orderRepository = new OrderRepositoryImpl();
    PointDetailRepository pointDetailRepository = new PointDetailRepositoryImpl();

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

    PointDetail testPointDetail = PointDetail.builder()
            .pointDetailID(1)
            .volume(30)
            .type(PointDetail.Type.POINT_SAVE)
            .createdAt(LocalDateTime.now())
            .uniqueUserID(testUser.getUniqueUserID())
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

        sql = "ALTER TABLE PointDetails AUTO_INCREMENT = 1";
        statement = connection.createStatement();
        statement.execute(sql);

        connection.close();

        DbConnectionThreadLocal.initialize();

        userRepository.save(testUser);
        addressRepository.saveAddress(testAddress);
        orderRepository.save(testOrder);
        pointDetailRepository.savePointDetail(testPointDetail);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("포인트 기록시 id 값 2을 반환")
    @org.junit.jupiter.api.Order(1)
    void save() {
        // given
        PointDetail newPointDetail = PointDetail.builder()
                .pointDetailID(2)
                .volume(12)
                .type(PointDetail.Type.POINT_USE)
                .createdAt(LocalDateTime.now())
                .uniqueUserID(testUser.getUniqueUserID())
                .build();

        // when
        int id = pointDetailRepository.savePointDetail(newPointDetail);

        // then
        assertEquals(2, id);
    }

    @Test
    @DisplayName("포인트 내역에 대한 PointDetail 페이지 가져오기")
    @org.junit.jupiter.api.Order(4)
    void pagination() {
        // given
        PointDetail newPointDetail = PointDetail.builder()
                .pointDetailID(2)
                .volume(12)
                .type(PointDetail.Type.POINT_USE)
                .createdAt(LocalDateTime.now())
                .uniqueUserID(testUser.getUniqueUserID())
                .build();

        pointDetailRepository.savePointDetail(newPointDetail);

        // when
        Page<PointDetail> pointPage = pointDetailRepository.getPointDetailPageByUserID(testUser.getUniqueUserID(), 1, 10);

        // then
        assertAll(
                () -> assertEquals(2, pointPage.getContent().size()),
                () -> assertEquals(2, pointPage.getTotalCount())
        );
    }

    @Test
    @DisplayName("현재 유저 포인트 가져오기")
    @org.junit.jupiter.api.Order(4)
    void get_current_point() {
        // given
        PointDetail newPointDetail = PointDetail.builder()
                .pointDetailID(2)
                .volume(-12)
                .type(PointDetail.Type.POINT_USE)
                .createdAt(LocalDateTime.now())
                .uniqueUserID(testUser.getUniqueUserID())
                .build();

        pointDetailRepository.savePointDetail(newPointDetail);

        // when
        int point = pointDetailRepository.getCurrentPointAmountByUserID(testUser.getUniqueUserID());

        // then
        assertEquals(18, point);
    }

}