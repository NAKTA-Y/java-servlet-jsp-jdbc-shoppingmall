package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.InvalidParameterException;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.page.Pagination;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.repository.product.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductCategoriesRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.service.product.CategoryService;
import com.nhnacademy.shoppingmall.service.product.ProductCategoriesService;
import com.nhnacademy.shoppingmall.service.product.ProductService;
import com.nhnacademy.shoppingmall.service.product.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.service.product.impl.ProductCategoriesServiceImpl;
import com.nhnacademy.shoppingmall.service.product.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@RequestMapping(value = "/product/list.do", method = RequestMapping.Method.GET)
public class ProductListController implements BaseController {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 12;

    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final ProductCategoriesService productCategoriesService = new ProductCategoriesServiceImpl(new ProductCategoriesRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String category = req.getParameter("category");
            String pageParam = req.getParameter("page");
            String pageSizeParam = req.getParameter("pageSize");

            int page = Objects.isNull(pageParam) || pageParam.isEmpty() ? DEFAULT_PAGE : Integer.parseInt(pageParam);
            int pageSize = Objects.isNull(pageSizeParam) || pageSizeParam.isEmpty() ? DEFAULT_PAGE_SIZE : Integer.parseInt(pageSizeParam);

            List<Category> categories = categoryService.findAll();

            Page<Product> productPage;
            productPage = Objects.isNull(category) || category.isEmpty()
                    ? productService.getProductPage(page, pageSize)
                    : productCategoriesService.getProductsByCategoryID(Integer.parseInt(category), page, pageSize);

            List<Product> products = productPage.getContent();

            req.setAttribute("category", category);
            req.setAttribute("categories", categories);
            req.setAttribute("products", products);
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", pageSize);
            req.setAttribute("totalPage", Pagination.getTotalPage(productPage.getTotalCount(), pageSize));
            req.setAttribute("startPage", Pagination.getStartPage(page));
            req.setAttribute("lastPage", Pagination.getLastPage(productPage.getTotalCount(), page, pageSize));

        } catch (NumberFormatException e) {
            throw InvalidParameterException.numberFormatException();
        }

        return "/shop/product/list";
    }
}
