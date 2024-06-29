package com.nhnacademy.shoppingmall.repository.product;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    int save(Category category);
    int update(Category category);
    Optional<Category> getByID(int categoryID);
    int deleteByID(int id);
    List<Category> findAll();
}
