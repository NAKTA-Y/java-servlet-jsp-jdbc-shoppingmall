package com.nhnacademy.shoppingmall.repository.product.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.util.DbUtils;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.repository.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ProductRepositoryImplTest {
    ProductRepository productRepository = new ProductRepositoryImpl();

    Product testProduct;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testProduct = Product.builder()
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

        Connection connection = DbUtils.getDataSource().getConnection();

        String sql = "ALTER TABLE Products AUTO_INCREMENT = 2";
        Statement statement = connection.createStatement();
        statement.execute(sql);

        connection.close();

        productRepository.save(testProduct);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("Product 저장시 ID 값은 3을 반환")
    @Order(1)
    void save() {
        // given
        Product newProduct = Product.builder()
                .productID(3)
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

        // when
        int id = productRepository.save(newProduct);

        // then
        assertEquals(newProduct.getProductID(), id);
    }

    @Test
    @DisplayName("Product 조회시 testProduct와 같음")
    @Order(2)
    void findByID() {
        // given
        // when
        Optional<Product> findProduct = productRepository.getByID(testProduct.getProductID());

        // then
        assertAll(
                () -> assertTrue(findProduct.isPresent()),
                () -> assertEquals(testProduct, findProduct.get())
        );
    }

    @Test
    @DisplayName("Product 업데이트시 1을 반환하고 업데이트 내용이 같음")
    @Order(3)
    void update() {
        // given
        String updateModelName = "updateModelName";
        testProduct.updateProductInfo(updateModelName, testProduct.getModelNumber(), testProduct.getDescription(), testProduct.getStock(), testProduct.getBrand());

        // when
        int result = productRepository.update(testProduct);

        // then
        Product findReview = productRepository.getByID(testProduct.getProductID()).get();
        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(updateModelName, findReview.getModelName())
        );
    }

    @Test
    @DisplayName("카운트시 1을 반환")
    @Order(4)
    void count() {
        // given
        // when
        int count = productRepository.countAll();

        // then
        assertEquals(1, count);
    }

    @Test
    @DisplayName("Product 삭제시 1 Return, Count = 0, State = DELETED")
    @Order(5)
    void delete() {
        // given
        // when
        int result = productRepository.deleteByID(testProduct.getProductID());

        // then
        Product product = productRepository.getByID(testProduct.getProductID()).get();
        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(Product.State.DELETED, product.getState())
        );
    }

    @ParameterizedTest(name = "{index} page:{0}, rows:{1} ")
    @MethodSource("paginationArguments")
    @DisplayName("pagination test")
    @Order(6)
    void pagination(int page, int rows) {
        Page<Product> userPage = productRepository.findAll(page, 10);
        Assertions.assertEquals(rows, userPage.getContent().size());
    }

    private static Stream<Arguments> paginationArguments(){
        return     Stream.of(
                Arguments.of(1,1),
                Arguments.of(2,0),
                Arguments.of(3,0),
                Arguments.of(4,0),
                Arguments.of(5,0),
                Arguments.of(6,0),
                Arguments.of(7,0),
                Arguments.of(8,0),
                Arguments.of(9,0),
                Arguments.of(10,0)
        );
    }

    @Test
    @Order(7)
    @DisplayName("banner 10개 가져오기")
    void getBanners() {
        List<Product> banners = productRepository.getBanners();
        assertEquals(1, banners.size());
    }

    @Test
    @Order(8)
    @DisplayName("Top View 10개 가져오기")
    void getTopViewProducts() {
        List<Product> topViewProducts = productRepository.getTopViewProducts(10);
        assertEquals(1, topViewProducts.size());
    }

    @Test
    @Order(9)
    @DisplayName("get products by id list")
    void getProductsByIDList() {
        // given
        Product newProduct = Product.builder()
                .productID(3)
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

        productRepository.save(newProduct);

        List<Integer> idList = new ArrayList<>();
        idList.add(2);
        idList.add(3);

        // when
        List<Product> products = productRepository.findAllByIDList(idList);

        // then
        assertEquals(2, products.size());
    }

    @Test
    @Order(10)
    @DisplayName("get products by keyword")
    void get_products_by_keyword() {
        // given
        Product newProduct = Product.builder()
                .productID(3)
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

        productRepository.save(newProduct);

        // when
        Page<Product> products = productRepository.getProductByKeyword("test", 1, 10);

        // then
        assertEquals(2, products.getContent().size());
    }

}