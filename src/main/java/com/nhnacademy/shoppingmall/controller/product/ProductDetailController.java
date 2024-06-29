package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.InvalidParameterException;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.PageNotFoundException;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.product.ProductImage;
import com.nhnacademy.shoppingmall.entity.product.Review;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductImageRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ReviewRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.user.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.service.product.ProductImageService;
import com.nhnacademy.shoppingmall.service.product.ProductService;
import com.nhnacademy.shoppingmall.service.product.ReviewService;
import com.nhnacademy.shoppingmall.service.product.impl.ProductImageServiceImpl;
import com.nhnacademy.shoppingmall.service.product.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.service.product.impl.ReviewServiceImpl;
import com.nhnacademy.shoppingmall.service.user.UserService;
import com.nhnacademy.shoppingmall.service.user.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequestMapping(value = "/product/detail.do", method = RequestMapping.Method.GET)
public class ProductDetailController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final ProductImageService productImageService = new ProductImageServiceImpl(new ProductImageRepositoryImpl());
    private final ReviewService reviewService = new ReviewServiceImpl(new ReviewRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String productIDParam = req.getParameter("product_id");
            String pageParam = req.getParameter("page");
            String pageSizeParam = req.getParameter("pageSize");

            if (Objects.isNull(productIDParam) || productIDParam.isEmpty())
                throw new PageNotFoundException();

            int productID = Integer.parseInt(productIDParam);
            int page;
            int pageSize;

            if (Objects.isNull(pageParam) || pageParam.isEmpty())
                page = 1;
            else
                page = Integer.parseInt(pageParam);

            if (Objects.isNull(pageSizeParam) || pageSizeParam.isEmpty())
                pageSize = 5;
            else
                pageSize = Integer.parseInt(pageSizeParam);


            Product product = productService.getProduct(productID);
            productService.increaseViewCount(productID);

            List<ProductImage> productImages = productImageService.getProductImagesByProductID(productID);
            Page<Review> reviewPage = reviewService.getReviewPage(productID, page, pageSize);
            List<Review> reviews = reviewPage.getContent();

            List<String> uniqueUserIDs = new ArrayList<>();
            reviews.forEach(r -> uniqueUserIDs.add(r.getUserID()));
            List<User> users = userService.getUsersByID(uniqueUserIDs);

            Map<String, User> userMap = new HashMap<>();
            users.forEach(u -> userMap.put(u.getUniqueUserID(), u));

            int totalPage = (int) Math.ceil((double) reviewPage.getTotalCount() / pageSize);
            int pageGroupIndex = ((page - 1) / 10) + 1;
            int startPage = (pageGroupIndex - 1) * 10 + 1;
            int lastPage = Math.min(startPage + 9, totalPage);

            req.setAttribute("product", product);
            req.setAttribute("productImages", productImages);
            req.setAttribute("reviews", reviews);
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", pageSize);
            req.setAttribute("totalPage", totalPage);
            req.setAttribute("startPage", startPage);
            req.setAttribute("lastPage", lastPage);
            req.setAttribute("userMap", userMap);

        } catch (NumberFormatException e) {
            throw InvalidParameterException.numberFormatException();
        }

        return "/shop/product/detail";
    }
}
