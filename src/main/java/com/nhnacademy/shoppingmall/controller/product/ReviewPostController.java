package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.InvalidParameterException;
import com.nhnacademy.shoppingmall.entity.product.Review;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.product.impl.ReviewRepositoryImpl;
import com.nhnacademy.shoppingmall.service.product.ReviewService;
import com.nhnacademy.shoppingmall.service.product.impl.ReviewServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping(value = "/reviewAction.do", method = RequestMapping.Method.POST)
public class ReviewPostController implements BaseController {
    private final ReviewService reviewService = new ReviewServiceImpl(new ReviewRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String comments = req.getParameter("comments");
            int rating = Integer.parseInt(req.getParameter("rating"));
            int productID = Integer.parseInt(req.getParameter("product_id"));

            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            Review newReview = Review.builder()
                    .userID(user.getUniqueUserID())
                    .productID(productID)
                    .comments(comments)
                    .rating(rating)
                    .build();

            reviewService.saveReview(newReview);

            return "redirect:/product/detail.do?product_id=" + productID;
        } catch (NumberFormatException e) {
            throw InvalidParameterException.numberFormatException();
        }
    }
}
