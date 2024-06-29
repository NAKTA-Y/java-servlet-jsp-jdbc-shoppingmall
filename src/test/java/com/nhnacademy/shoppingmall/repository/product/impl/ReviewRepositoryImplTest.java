package com.nhnacademy.shoppingmall.repository.product.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.util.DbUtils;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.product.Review;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.product.ProductRepository;
import com.nhnacademy.shoppingmall.repository.product.ReviewRepository;
import com.nhnacademy.shoppingmall.repository.user.UserRepository;
import com.nhnacademy.shoppingmall.repository.user.impl.UserRepositoryImpl;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class ReviewRepositoryImplTest {
    ProductRepository productRepository = new ProductRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();
    ReviewRepository reviewRepository = new ReviewRepositoryImpl();

    User testUser;
    Product testProduct;
    Review testReview;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testUser = User.builder()
                .uniqueUserID("uniqueID")
                .userID("userID")
                .userName("userName")
                .userPassword("userPassword")
                .userBirth("19990101")
                .userAuth(User.Auth.ROLE_USER)
                .userTelephone("010-1234-1234")
                .createdAt(LocalDateTime.now())
                .state(User.State.ACTIVE)
                .build();

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

        testReview = Review.builder()
                .reviewID(2)
                .rating(5)
                .comments("testComments")
                .recommend(0)
                .productID(testProduct.getProductID())
                .userID(testUser.getUniqueUserID())
                .build();

        Connection connection = DbUtils.getDataSource().getConnection();

        String sql = "ALTER TABLE Products AUTO_INCREMENT = 1";
        Statement statement = connection.createStatement();
        statement.execute(sql);

        sql = "ALTER TABLE Reviews AUTO_INCREMENT = 2";
        statement = connection.createStatement();
        statement.execute(sql);

        connection.close();

        userRepository.save(testUser);
        productRepository.save(testProduct);
        reviewRepository.save(testReview);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("Review 저장시 ID 값은 3를 반환")
    void save() {
        // given
        Review newReview = Review.builder()
                .reviewID(3)
                .rating(5)
                .comments("testComments")
                .recommend(0)
                .productID(testProduct.getProductID())
                .userID(testUser.getUniqueUserID())
                .build();

        // when
        int id = reviewRepository.save(newReview);

        // then
        assertEquals(newReview.getReviewID(), id);
    }

    @Test
    @Order(2)
    @DisplayName("get review")
    void getReview() {
        Optional<Review> reviewOptional = reviewRepository.getByID(testReview.getReviewID());

        assertAll(
                () -> assertTrue(reviewOptional.isPresent()),
                () -> assertEquals(testReview, reviewOptional.get())
        );
    }

    @Test
    @Order(3)
    @DisplayName("Review 삭제시 1을 반환")
    void delete() {
        // given
        // when
        int result = reviewRepository.deleteByID(testReview.getReviewID());

        // then
        assertEquals(1, result);
    }

    @Test
    @Order(4)
    @DisplayName("review recommend plus")
    void recommendCount() {
        // given
        // when
        reviewRepository.plusRecommendCount(testReview.getReviewID());

        // then
        Review review = reviewRepository.getByID(testReview.getReviewID()).get();
        assertEquals(1, review.getRecommend());
    }

    @Test
    @Order(5)
    @DisplayName("get review page")
    void pagination() {
        // given
        // when
        Page<Review> reviewPage = reviewRepository.getReviewsByProductID(testProduct.getProductID(), 1, 10);

        // then
        assertEquals(1, reviewPage.getContent().size());
        assertEquals(1, reviewPage.getTotalCount());
    }
}