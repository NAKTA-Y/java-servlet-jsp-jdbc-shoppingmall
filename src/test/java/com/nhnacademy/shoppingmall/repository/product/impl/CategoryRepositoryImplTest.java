package com.nhnacademy.shoppingmall.repository.product.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.util.DbUtils;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.repository.product.CategoryRepository;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class CategoryRepositoryImplTest {
    CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    Category testCategory;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testCategory = new Category(2, "testCategory");

        Connection connection = DbUtils.getDataSource().getConnection();
        String sql = "ALTER TABLE Categories AUTO_INCREMENT = 2";
        Statement statement = connection.createStatement();
        statement.execute(sql);
        connection.close();

        categoryRepository.save(testCategory);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("[성공] 카테고리 등록시 PK를 반환")
    void save() {
        // given
        Category newCategory = new Category(3, "newCategory");

        // when
        int pk = categoryRepository.save(newCategory);

        // then
        assertEquals(3, pk);
    }

    @Test
    @Order(2)
    @DisplayName("[실패] null 등록시 Exception 발생")
    void save_null() {
        // given
        // when
        // then
        assertThrows(RuntimeException.class, () -> categoryRepository.save(null));
    }

    @Test
    @Order(3)
    @DisplayName("[성공] 카테고리 조회시 객체를 반환")
    void getByID() {
        // given
        // when
        Category findCategory = categoryRepository.getByID(testCategory.getCategoryID()).get();

        // then
        assertEquals(testCategory, findCategory);
    }

    @Test
    @Order(4)
    @DisplayName("[실패] 잘못된 ID로 카테고리 조회시 빈 값을 반환")
    void getByID_fail() {
        // given
        // when
        Optional<Category> findCategoryOptional = categoryRepository.getByID(4);

        // then
        assertEquals(Optional.empty(), findCategoryOptional);
    }

    @Test
    @Order(5)
    @DisplayName("[성공] 카테고리 수정시 1을 반환하고 수정이 반영됨")
    void update() {
        // given
        String updateCategoryName = "updatedCategory";
        testCategory.updateCategoryName(updateCategoryName);

        // when
        int result = categoryRepository.update(testCategory);

        // then
        Category findCategory = categoryRepository.getByID(testCategory.getCategoryID()).get();
        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(updateCategoryName, findCategory.getCategoryName())
        );
    }

    @Test
    @Order(6)
    @DisplayName("[실패] 카테고리 수정시 50자를 넘기면 Exception 발생")
    void update_over_character_length() {
        // given
        testCategory.updateCategoryName("lllllllllllllllllllllllllllllllllllllllllllllllllll");

        // when
        // then
        assertThrows(RuntimeException.class, () -> categoryRepository.update(testCategory));
    }

    @Test
    @Order(7)
    @DisplayName("[성공] 카테고리 삭제시 1을 반환")
    void delete() {
        // given
        // when
        int result = categoryRepository.deleteByID(testCategory.getCategoryID());

        // then
        assertEquals(1, result);
    }

    @Test
    @Order(8)
    @DisplayName("[실패] 잘못된 ID로 카테고리 삭제시 0 반환")
    void delete_fail() {
        // given
        // when
        int result = categoryRepository.deleteByID(4);

        // then
        assertEquals(0, result);
    }

    @Test
    @Order(9)
    @DisplayName("[성공] 카테고리 모두 조회")
    void findAll() {
        // given
        // when
        List<Category> categoryPage = categoryRepository.findAll();

        // then
        Assertions.assertEquals(1, categoryPage.size());
    }
}