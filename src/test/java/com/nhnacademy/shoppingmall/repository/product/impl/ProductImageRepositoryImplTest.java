package com.nhnacademy.shoppingmall.repository.product.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.util.DbUtils;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.product.ProductImage;
import com.nhnacademy.shoppingmall.repository.product.ProductImageRepository;
import com.nhnacademy.shoppingmall.repository.product.ProductRepository;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductImageRepositoryImplTest {
    ProductImageRepository productImageRepository = new ProductImageRepositoryImpl();
    ProductImage testProductImage;

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

        testProductImage = ProductImage.builder()
                .productImageID(2)
                .productImage("/images")
                .imageSize(122)
                .productID(testProduct.getProductID())
                .build();

        Connection connection = DbUtils.getDataSource().getConnection();

        String sql = "ALTER TABLE Products AUTO_INCREMENT = 2";
        Statement statement = connection.createStatement();
        statement.execute(sql);

        sql = "ALTER TABLE ProductImages AUTO_INCREMENT = 2";
        statement = connection.createStatement();
        statement.execute(sql);

        connection.close();

        productRepository.save(testProduct);
        productImageRepository.save(testProductImage);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("save - return pk")
    void save() {
        // given
        ProductImage newProductImage = ProductImage.builder()
                .productImageID(3)
                .productImage("/images")
                .imageSize(12)
                .productID(testProduct.getProductID())
                .build();

        // when
        int pk = productImageRepository.save(newProductImage);

        // then
        assertEquals(3, pk);
    }

    @Test
    @Order(2)
    @DisplayName("delete - return 1")
    void delete() {
        // given
        // when
        int result = productImageRepository.deleteByID(testProductImage.getProductImageID());

        // then
        assertEquals(1, result);
    }

    @Test
    @Order(3)
    @DisplayName("get product images - return list")
    void get_product_images_by_product_id() {
        // given
        // when
        List<ProductImage> productImages = productImageRepository.getProductImagesByProductID(testProduct.getProductID());

        // then
        assertAll(
                () -> assertEquals(1, productImages.size()),
                () -> assertEquals(testProductImage, productImages.get(0))
        );
    }
}