package com.nhnacademy.shoppingmall.service.product.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.product.Review;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.product.ProductRepository;
import com.nhnacademy.shoppingmall.repository.product.ReviewRepository;
import com.nhnacademy.shoppingmall.repository.user.UserRepository;
import com.nhnacademy.shoppingmall.service.product.ProductService;
import com.nhnacademy.shoppingmall.service.product.ReviewService;
import com.nhnacademy.shoppingmall.service.user.UserService;
import com.nhnacademy.shoppingmall.service.user.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

class ReviewServiceImplTest {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(userRepository);
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


    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ProductService productService = new ProductServiceImpl(productRepository);
    Product testProduct = Product.builder()
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

    ReviewRepository reviewRepository = Mockito.mock(ReviewRepository.class);
    ReviewService reviewService = new ReviewServiceImpl(reviewRepository);
    Review testReview = Review.builder()
            .reviewID(2)
            .rating(5)
            .comments("testComments")
            .recommend(0)
            .productID(1)
            .userID("uniqueID")
            .build();

    @BeforeEach
    void setUp() {
        userService.saveUser(testUser);
        productService.saveProduct(testProduct);
    }

    @Test
    @DisplayName("save review")
    void saveReview() {
        Mockito.when(reviewRepository.save(any())).thenReturn(testReview.getReviewID());
        reviewService.saveReview(testReview);
        Mockito.verify(reviewRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("delete review")
    void deleteReview() {
        Mockito.when(reviewRepository.deleteByID(anyInt())).thenReturn(1);
        reviewService.deleteReview(testReview.getReviewID());
        Mockito.verify(reviewRepository, Mockito.times(1)).deleteByID(anyInt());
    }

    @Test
    @DisplayName("plus recommend")
    void plusRecommend() {
        Mockito.when(reviewRepository.plusRecommendCount(anyInt())).thenReturn(1);
        reviewService.plusRecommendCount(testReview.getReviewID());
        Mockito.verify(reviewRepository, Mockito.times(1)).plusRecommendCount(anyInt());
    }

    @Test
    @DisplayName("pagination")
    void pagination() {
        List<Review> reviews = new ArrayList<>();
        Page<Review> reviewPage = new Page<>(reviews, 0);

        Mockito.when(reviewRepository.getReviewsByProductID(anyInt(), anyInt(), anyInt())).thenReturn(reviewPage);
        reviewService.getReviewPage(testProduct.getProductID(), 1, 10);
        Mockito.verify(reviewRepository, Mockito.times(1)).getReviewsByProductID(anyInt(), anyInt(), anyInt());

    }
}