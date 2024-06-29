package com.nhnacademy.shoppingmall.service.product.impl;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Review;
import com.nhnacademy.shoppingmall.exception.product.ReviewNoEffectException;
import com.nhnacademy.shoppingmall.exception.product.ReviewNotFoundException;
import com.nhnacademy.shoppingmall.repository.product.ReviewRepository;
import com.nhnacademy.shoppingmall.service.product.ReviewService;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ReviewServiceImpl implements ReviewService {
    private static final String REVIEW_SAVE_EXCEPTION_MESSAGE = "리뷰를 생성하지 못했습니다.";
    private static final String REVIEW_UPDATE_EXCEPTION_MESSAGE = "리뷰를 업데이트를 실패했습니다.";
    private static final String REVIEW_DELETE_EXCEPTION_MESSAGE = "리뷰를 삭제하지 못했습니다.";
    private static final String REVIEW_NOT_FOUND_EXCEPTION_MESSAGE = "리뷰를 찾지 못했습니다.";

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void saveReview(Review review) {
        int pk = reviewRepository.save(review);
        if (pk < 1)
            throw new ReviewNoEffectException(HttpServletResponse.SC_NO_CONTENT, REVIEW_SAVE_EXCEPTION_MESSAGE);
    }

    @Override
    public void deleteReview(int reviewID) {
        Optional<Review> reviewOptional = reviewRepository.getByID(reviewID);
        if (reviewOptional.isEmpty())
            throw new ReviewNotFoundException(HttpServletResponse.SC_NOT_FOUND, REVIEW_NOT_FOUND_EXCEPTION_MESSAGE);

        int result = reviewRepository.deleteByID(reviewID);
        if (result < 1)
            throw new ReviewNoEffectException(HttpServletResponse.SC_NO_CONTENT, REVIEW_DELETE_EXCEPTION_MESSAGE);
    }

    @Override
    public void plusRecommendCount(int reviewID) {
        Optional<Review> reviewOptional = reviewRepository.getByID(reviewID);
        if (reviewOptional.isEmpty())
            throw new ReviewNotFoundException(HttpServletResponse.SC_NOT_FOUND, REVIEW_NOT_FOUND_EXCEPTION_MESSAGE);

        int result = reviewRepository.plusRecommendCount(reviewID);
        if (result < 1)
            throw new ReviewNoEffectException(HttpServletResponse.SC_NO_CONTENT, REVIEW_UPDATE_EXCEPTION_MESSAGE);
    }

    @Override
    public Page<Review> getReviewPage(int productID, int page, int pageSize) {
        return reviewRepository.getReviewsByProductID(productID, page, pageSize);
    }
}
