package com.nhnacademy.shoppingmall.service.product;

import com.nhnacademy.shoppingmall.entity.product.Category;

import java.util.List;

public interface CategoryService {
    void saveCategory(Category category);
    Category getCategory(int categoryID);
    void updateCategory(Category category);
    void deleteCategory(int categoryID);
    List<Category> findAll();
}
