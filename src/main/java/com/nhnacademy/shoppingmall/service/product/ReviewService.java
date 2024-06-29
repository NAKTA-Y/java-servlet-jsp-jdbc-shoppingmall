package com.nhnacademy.shoppingmall.service.product;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Review;

public interface ReviewService {
    void saveReview(Review review);
    void deleteReview(int reviewID);
    void plusRecommendCount(int reviewID);

    Page<Review> getReviewPage(int productID, int page, int pageSize);
}
