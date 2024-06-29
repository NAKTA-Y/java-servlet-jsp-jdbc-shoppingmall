package com.nhnacademy.shoppingmall.repository.cart.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.util.DbUtils;
import com.nhnacademy.shoppingmall.entity.cart.ShoppingCart;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.cart.ShoppingCartRepository;
import com.nhnacademy.shoppingmall.repository.product.ProductRepository;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.user.UserRepository;
import com.nhnacademy.shoppingmall.repository.user.impl.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ShoppingCartRepositoryImplTest {
    ShoppingCartRepository shoppingCartRepository = new ShoppingCartRepositoryImpl();
    ShoppingCart testShoppingCart;

    ProductRepository productRepository = new ProductRepositoryImpl();
    Product testProduct;

    UserRepository userRepository = new UserRepositoryImpl();
    User testUser;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testProduct = Product.builder()
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

        testShoppingCart = ShoppingCart.builder()
                .uniqueUserID(testUser.getUniqueUserID())
                .productID(testProduct.getProductID())
                .quantity(1)
                .createdAt(LocalDateTime.now())
                .build();

        Connection connection = DbUtils.getDataSource().getConnection();
        String sql = "ALTER TABLE Products AUTO_INCREMENT = 1";
        Statement statement = connection.createStatement();
        statement.execute(sql);
        connection.close();

        productRepository.save(testProduct);
        userRepository.save(testUser);
        shoppingCartRepository.save(testShoppingCart);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("[성공] save")
    void save() {
        // given
        User newUser = User.builder()
                .uniqueUserID("nhnacademy-unique-id2")
                .userID("nhnacademy-test-user2")
                .userName("nhn아카데미2")
                .userPassword("nhnacademy-test-password2")
                .userBirth("19990101")
                .userTelephone("010-1234-1234")
                .userAuth(User.Auth.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .state(User.State.ACTIVE)
                .build();

        ShoppingCart newShoppingCart = ShoppingCart.builder()
                .uniqueUserID(newUser.getUniqueUserID())
                .productID(testProduct.getProductID())
                .quantity(1)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(newUser);

        // when
        int pk = shoppingCartRepository.save(newShoppingCart);

        // then
        assertEquals(1, pk);
    }

    @Test
    @Order(2)
    @DisplayName("[실패] save - constraints 확인")
    void save_fail_constraints() {
        // given
        ShoppingCart newShoppingCart = ShoppingCart.builder()
                .uniqueUserID("dfmkalf")
                .productID(testProduct.getProductID())
                .quantity(1)
                .createdAt(LocalDateTime.now())
                .build();

        // when
        // then
        assertThrows(RuntimeException.class, () -> shoppingCartRepository.save(newShoppingCart));
    }

    @Test
    @Order(3)
    @DisplayName("[성공] delete")
    void delete() {
        // given
        // when
        int result = shoppingCartRepository.deleteByUserIDAndProductID(testUser.getUniqueUserID(), testProduct.getProductID());

        // then
        assertEquals(1, result);
    }

    @Test
    @Order(4)
    @DisplayName("[실패] delete - false key")
    void delete_false_key() {
        // given
        // when
        int result = shoppingCartRepository.deleteByUserIDAndProductID("fdmkalf", 5);

        // then
        assertEquals(0, result);
    }

    @Test
    @Order(5)
    @DisplayName("[성공] 특정 유저에 대한 장바구니 조회")
    void getCategoriesByProductID() {
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

        ShoppingCart newShoppingCart = ShoppingCart.builder()
                .uniqueUserID(testShoppingCart.getUniqueUserID())
                .productID(newProduct.getProductID())
                .quantity(1)
                .createdAt(LocalDateTime.now())
                .build();

        productRepository.save(newProduct);
        shoppingCartRepository.save(newShoppingCart);

        // when
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.getShoppingCartByUserID(testUser.getUniqueUserID());

        // then
        assertEquals(2, shoppingCarts.size());
    }

    @Test
    @Order(6)
    @DisplayName("[실패] 없는 유저에 대한 상품 조회")
    void getCategoriesByProductID_false_key() {
        // given
        // when
        List<ShoppingCart> products = shoppingCartRepository.getShoppingCartByUserID("notFound");

        // then
        assertEquals(0, products.size());
    }

    @Test
    @Order(7)
    @DisplayName("[성공] 해당 상품 카테고리가 있으면 True")
    void isExist_true() {
        // given
        // when
        boolean exist = shoppingCartRepository.isExist(testUser.getUniqueUserID(), testProduct.getProductID());

        // then
        assertTrue(exist);
    }

    @Test
    @Order(8)
    @DisplayName("[성공] 해당 상품 카테고리가 없으면 false")
    void isExist_false() {
        // given
        // when
        boolean exist = shoppingCartRepository.isExist("notFound", 5);

        // then
        assertFalse(exist);
    }

    @Test
    @Order(9)
    @DisplayName("[성공] 상품 수량 변경")
    void update_quantity() {
        // given
        int updateQuantity = 5;

        // when
        int result = shoppingCartRepository.updateQuantity(testUser.getUniqueUserID(), testProduct.getProductID(), updateQuantity);

        // then
        assertEquals(1, result);
    }
}