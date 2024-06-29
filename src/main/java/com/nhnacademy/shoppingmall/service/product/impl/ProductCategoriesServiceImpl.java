package com.nhnacademy.shoppingmall.service.product.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.exception.product.ProductCategoryAlreadyExistsException;
import com.nhnacademy.shoppingmall.exception.product.ProductCategoryNoEffectException;
import com.nhnacademy.shoppingmall.exception.product.ProductCategoryNotFoundException;
import com.nhnacademy.shoppingmall.repository.product.ProductCategoriesRepository;
import com.nhnacademy.shoppingmall.service.product.ProductCategoriesService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ProductCategoriesServiceImpl implements ProductCategoriesService {
    private static final String PRODUCT_CATEGORY_SAVE_EXCEPTION_MESSAGE = "카테고리 항목을 추가하지 못했습니다.";
    private static final String PRODUCT_CATEGORY_NOT_FOUND_EXCEPTION_MESSAGE = "카테고리 항목을 찾지 못했습니다.";
    private static final String PRODUCT_CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE = "이미 존재하는 카테고리 입니다.";

    private final ProductCategoriesRepository categoriesRepository;

    public ProductCategoriesServiceImpl(ProductCategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public void saveProductCategory(int productID, int categoryID) {
        if (categoriesRepository.isExist(productID, categoryID)) {
            throw new ProductCategoryAlreadyExistsException(HttpServletResponse.SC_CONFLICT, PRODUCT_CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE);
        }

        int result = categoriesRepository.save(productID, categoryID);
        if (result < 1)
            throw new ProductCategoryNoEffectException(HttpServletResponse.SC_NO_CONTENT, PRODUCT_CATEGORY_SAVE_EXCEPTION_MESSAGE);
    }

    @Override
    public void deleteProductCategory(int productID, int categoryID) {
        if (!categoriesRepository.isExist(productID, categoryID)) {
            throw new ProductCategoryNotFoundException(HttpServletResponse.SC_NOT_FOUND, PRODUCT_CATEGORY_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        int result = categoriesRepository.delete(productID, categoryID);
        if (result < 1)
            throw new ProductCategoryNoEffectException(HttpServletResponse.SC_NO_CONTENT, PRODUCT_CATEGORY_SAVE_EXCEPTION_MESSAGE);
    }

    @Override
    public List<Category> getCategoriesByProductID(int productID) {
        return categoriesRepository.findCategoriesByProductID(productID);
    }

    @Override
    public Page<Product> getProductsByCategoryID(int categoryID, int page, int pageSize) {
        return categoriesRepository.findProductsByCategoryID(categoryID, page, pageSize);
    }

    @Override
    public boolean isExist(int productID, int categoryID) {
        return categoriesRepository.isExist(productID, categoryID);
    }
}
