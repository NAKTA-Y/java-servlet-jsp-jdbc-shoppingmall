package com.nhnacademy.shoppingmall.repository.user.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.util.DbUtils;
import com.nhnacademy.shoppingmall.entity.user.Address;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.user.AddressRepository;
import com.nhnacademy.shoppingmall.repository.user.UserRepository;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AddressRepositoryImplTest {
    UserRepository userRepository = new UserRepositoryImpl();
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

    AddressRepository addressRepository = new AddressRepositoryImpl();

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

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        Connection connection = DbUtils.getDataSource().getConnection();
        String sql = "ALTER TABLE Addresses AUTO_INCREMENT = 1";
        Statement statement = connection.createStatement();
        statement.execute(sql);
        connection.close();

        userRepository.save(testUser);
        addressRepository.saveAddress(testAddress);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("Address 저장시 ID 값은 2를 반환")
    void save() {
        // given
        Address newAddress = Address.builder()
                .addressID(2)
                .address("testAddress")
                .addressDetail("testAddressDetail")
                .zipcode("123456")
                .name("testName")
                .telephone("010-1234-1234")
                .request("testRequest")
                .defaultAddress(false)
                .uniqueUserID(testUser.getUniqueUserID())
                .build();


        // when
        int pk = addressRepository.saveAddress(newAddress);

        // then
        assertEquals(newAddress.getAddressID(), pk);
    }

    @Test
    @Order(2)
    @DisplayName("get address")
    void getReview() {
        Optional<Address> addressOptional = addressRepository.getAddressByID(testAddress.getAddressID());

        assertAll(
                () -> assertTrue(addressOptional.isPresent()),
                () -> assertEquals(testAddress, addressOptional.get())
        );
    }

    @Test
    @Order(3)
    @DisplayName("Address 삭제시 1을 반환")
    void delete() {
        // given
        // when
        int result = addressRepository.deleteAddressByID(testAddress.getAddressID());

        // then
        assertEquals(1, result);
    }

    @Test
    @Order(4)
    @DisplayName("Address update")
    void update() {
        // given
        String updateAddress = "updateAddress";
        testAddress.updateAdressInfo("updateAddress", testAddress.getAddressDetail(), testAddress.getZipcode(), testAddress.getName(), testAddress.getTelephone(), testAddress.getRequest());

        // when
        addressRepository.updateAddressInfo(testAddress);

        // then
        Address address = addressRepository.getAddressByID(testAddress.getAddressID()).get();
        assertEquals(updateAddress, address.getAddress());
    }

    @Test
    @Order(5)
    @DisplayName("Set Default Address")
    void set_default_address() {
        // given
        // when
        int result = addressRepository.setDefaultAddress(testAddress.getAddressID(), true);

        // then
        Address address = addressRepository.getAddressByID(testAddress.getAddressID()).get();
        assertEquals(1, result);
        assertTrue(address.isDefaultAddress());
    }

    @Test
    @Order(6)
    @DisplayName("Count Default Address")
    void count_default_address() {
        // given
        // when
        int count = addressRepository.countDefaultAddress(testUser.getUniqueUserID());

        // then
        assertEquals(0, count);
    }

    @Test
    @Order(7)
    @DisplayName("Count User Address")
    void count_user_address() {
        // given
        // when
        int count = addressRepository.countAddressByUserID(testUser.getUniqueUserID());

        // then
        assertEquals(1, count);
    }

    @Test
    @Order(8)
    @DisplayName("Get User Addresses")
    void get_user_addresses() {
        // given
        // when
        List<Address> addresses = addressRepository.getAddressesByUserID(testUser.getUniqueUserID());

        // then
        assertEquals(1, addresses.size());
        assertEquals(testAddress, addresses.get(0));
    }
}