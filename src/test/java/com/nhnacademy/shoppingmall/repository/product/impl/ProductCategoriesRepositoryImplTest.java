package com.nhnacademy.shoppingmall.repository.product.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.util.DbUtils;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.repository.product.CategoryRepository;
import com.nhnacademy.shoppingmall.repository.product.ProductCategoriesRepository;
import com.nhnacademy.shoppingmall.repository.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ProductCategoriesRepositoryImplTest {
    ProductCategoriesRepository productCategoriesRepository = new ProductCategoriesRepositoryImpl();
    ProductRepository productRepository = new ProductRepositoryImpl();
    CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    Product testProduct;
    Category testCategory;

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

        testCategory = new Category(1, "testCategory");

        Connection connection = DbUtils.getDataSource().getConnection();
        String sql = "ALTER TABLE Categories AUTO_INCREMENT = 1";
        Statement statement = connection.createStatement();
        statement.execute(sql);

        sql = "ALTER TABLE Products AUTO_INCREMENT = 1";
        statement = connection.createStatement();
        statement.execute(sql);
        connection.close();

        productRepository.save(testProduct);
        categoryRepository.save(testCategory);
        productCategoriesRepository.save(testProduct.getProductID(), testCategory.getCategoryID());
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
        Category newCategory = new Category(2, "newCategoryName");
        categoryRepository.save(newCategory);

        // when
        int result = productCategoriesRepository.save(testProduct.getProductID(), newCategory.getCategoryID());

        // then
        assertEquals(1, result);
    }

    @Test
    @Order(2)
    @DisplayName("[실패] save - constraints 확인")
    void save_fail_constraints() {
        // given
        // when
        // then
        assertThrows(RuntimeException.class, () -> productCategoriesRepository.save(testProduct.getProductID(), 4));
    }

    @Test
    @Order(3)
    @DisplayName("[성공] delete")
    void delete() {
        // given
        // when
        int result = productCategoriesRepository.delete(testProduct.getProductID(), testCategory.getCategoryID());

        // then
        assertEquals(1, result);
    }

    @Test
    @Order(4)
    @DisplayName("[실패] delete - false key")
    void delete_false_key() {
        // given
        // when
        int result = productCategoriesRepository.delete(5, 5);

        // then
        assertEquals(0, result);
    }

    @Test
    @Order(5)
    @DisplayName("[성공] 특정 상품에 대한 카테고리 조회")
    void getCategoriesByProductID() {
        // given
        Category newCategory1 = new Category(2, "newCategoryName1");
        Category newCategory2 = new Category(3, "newCategoryName2");

        categoryRepository.save(newCategory1);
        categoryRepository.save(newCategory2);

        productCategoriesRepository.save(testProduct.getProductID(), newCategory1.getCategoryID());
        productCategoriesRepository.save(testProduct.getProductID(), newCategory2.getCategoryID());

        // when
        List<Category> categories = productCategoriesRepository.findCategoriesByProductID(testProduct.getProductID());

        // then
        assertEquals(3, categories.size());

        for (Category category : categories) {
            log.info("{}", category);
        }
    }

    @Test
    @Order(6)
    @DisplayName("[실패] 없는 상품에 대한 카테고리 조회")
    void getCategoriesByProductID_false_key() {
        // given
        // when
        List<Category> categories = productCategoriesRepository.findCategoriesByProductID(5);

        // then
        assertEquals(0, categories.size());

        for (Category category : categories) {
            log.info("{}", category);
        }
    }

    @Test
    @Order(7)
    @DisplayName("[성공] 특정 카테고리에 대한 상품 페이지 조회")
    void getProductsByCategoryID() {
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

        productRepository.save(newProduct);
        productCategoriesRepository.save(newProduct.getProductID(), testCategory.getCategoryID());

        // when
        Page<Product> products = productCategoriesRepository.findProductsByCategoryID(testCategory.getCategoryID(), 1, 10);

        // then
        assertEquals(2, products.getContent().size());
    }

    @Test
    @Order(8)
    @DisplayName("[실패] 없는 카테고리에 대한 상품 조회")
    void getProductsByCategoryID_false_key() {
        // given
        // when
        Page<Product> products = productCategoriesRepository.findProductsByCategoryID(5, 1, 10);

        // then
        assertEquals(0, products.getContent().size());
    }

    @Test
    @Order(9)
    @DisplayName("[성공] 해당 상품 카테고리가 있으면 True")
    void isExist_true() {
        // given
        // when
        boolean exist = productCategoriesRepository.isExist(testProduct.getProductID(), testCategory.getCategoryID());

        // then
        assertTrue(exist);
    }

    @Test
    @Order(10)
    @DisplayName("[성공] 해당 상품 카테고리가 없으면 false")
    void isExist_false() {
        // given
        // when
        boolean exist = productCategoriesRepository.isExist(5, 5);

        // then
        assertFalse(exist);
    }
}