package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.page.Pagination;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.service.product.ProductService;
import com.nhnacademy.shoppingmall.service.product.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@RequestMapping(value = "/admin/product_list.do", method = RequestMapping.Method.GET)
public class ProductManagementController implements BaseController {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String pageParam = req.getParameter("page");
        String pageSizeParam = req.getParameter("pageSize");

        int page = Objects.isNull(pageParam) || pageParam.isEmpty() ? DEFAULT_PAGE : Integer.parseInt(pageParam);
        int pageSize = Objects.isNull(pageSizeParam) || pageSizeParam.isEmpty() ? DEFAULT_PAGE_SIZE : Integer.parseInt(pageSizeParam);

        Page<Product> productPage = productService.getProductPage(page, pageSize);
        List<Product> products = productPage.getContent();

        req.setAttribute("products", products);
        req.setAttribute("currentPage", page);
        req.setAttribute("pageSize", pageSize);
        req.setAttribute("totalPage", Pagination.getTotalPage(productPage.getTotalCount(), pageSize));
        req.setAttribute("startPage", Pagination.getStartPage(page));
        req.setAttribute("lastPage", Pagination.getLastPage(productPage.getTotalCount(), page, pageSize));

        return "/shop/admin/product_list";
    }
}
