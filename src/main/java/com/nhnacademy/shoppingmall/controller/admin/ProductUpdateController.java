package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.product.ProductImage;
import com.nhnacademy.shoppingmall.repository.product.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductCategoriesRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductImageRepositoryImpl;
import com.nhnacademy.shoppingmall.repository.product.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.service.product.CategoryService;
import com.nhnacademy.shoppingmall.service.product.ProductCategoriesService;
import com.nhnacademy.shoppingmall.service.product.ProductImageService;
import com.nhnacademy.shoppingmall.service.product.ProductService;
import com.nhnacademy.shoppingmall.service.product.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.service.product.impl.ProductCategoriesServiceImpl;
import com.nhnacademy.shoppingmall.service.product.impl.ProductImageServiceImpl;
import com.nhnacademy.shoppingmall.service.product.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@RequestMapping(value = "/admin/product_info.do", method = RequestMapping.Method.GET)
public class ProductUpdateController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final ProductImageService productImageService = new ProductImageServiceImpl(new ProductImageRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private final ProductCategoriesService productCategoriesService = new ProductCategoriesServiceImpl(new ProductCategoriesRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("product_id");

        if (Objects.isNull(productId)) {
            resp.setStatus(400);
            throw new RuntimeException("BAD REQUEST");
        }

        Product product = productService.getProduct(Integer.valueOf(productId));
        if (Objects.isNull(product)) {
            resp.setStatus(404);
            throw new RuntimeException("NOT FOUND");
        }

        List<ProductImage> productImages = productImageService.getProductImagesByProductID(product.getProductID());
        List<Category> categories = categoryService.findAll();
        List<Category> categoriesByProduct = productCategoriesService.getCategoriesByProductID(product.getProductID());

        req.setAttribute("product", product);
        req.setAttribute("productImages", productImages);
        req.setAttribute("categories", categories);
        req.setAttribute("categoriesByProduct", categoriesByProduct);

        return "/shop/admin/product_info";
    }
}
