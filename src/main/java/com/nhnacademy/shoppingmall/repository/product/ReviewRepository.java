package com.nhnacademy.shoppingmall.repository.product;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Review;

import java.util.Optional;

public interface ReviewRepository {
    int save(Review review);
    Optional<Review> getByID(int id);
    int deleteByID(int id);
    int plusRecommendCount(int id);

    Page<Review> getReviewsByProductID(int productID, int page, int pageSize);
}
