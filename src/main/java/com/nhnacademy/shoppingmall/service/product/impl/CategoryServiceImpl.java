package com.nhnacademy.shoppingmall.service.product.impl;

import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.exception.product.CategoryNoEffectException;
import com.nhnacademy.shoppingmall.exception.product.CategoryNotFoundException;
import com.nhnacademy.shoppingmall.repository.product.CategoryRepository;
import com.nhnacademy.shoppingmall.service.product.CategoryService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORY_SAVE_EXCEPTION_MESSAGE = "카테고리 항목을 추가하지 못했습니다.";
    private static final String CATEGORY_UPDATE_EXCEPTION_MESSAGE = "카테고리 항목을 수정하지 못했습니다.";
    private static final String CATEGORY_DELETE_EXCEPTION_MESSAGE = "카테고리 항목을 삭제하지 못했습니다.";
    private static final String CATEGORY_NOT_FOUND_EXCEPTION_MESSAGE = "카테고리 항목을 찾지 못했습니다.";

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void saveCategory(Category category) {
        int pk = categoryRepository.save(category);
        if (pk < 1) throw new CategoryNoEffectException(HttpServletResponse.SC_NO_CONTENT, CATEGORY_SAVE_EXCEPTION_MESSAGE);
    }

    @Override
    public Category getCategory(int categoryID) {
        Optional<Category> categoryOptional = categoryRepository.getByID(categoryID);
        if (categoryOptional.isEmpty())
            throw new CategoryNotFoundException(HttpServletResponse.SC_NOT_FOUND, CATEGORY_NOT_FOUND_EXCEPTION_MESSAGE);

        return categoryOptional.get();
    }

    @Override
    public void updateCategory(Category category) {
        int result = categoryRepository.update(category);
        if (result < 1) throw new CategoryNoEffectException(HttpServletResponse.SC_NO_CONTENT, CATEGORY_UPDATE_EXCEPTION_MESSAGE);
    }

    @Override
    public void deleteCategory(int categoryID) {
        int result = categoryRepository.deleteByID(categoryID);
        if (result < 1) throw new CategoryNoEffectException(HttpServletResponse.SC_NO_CONTENT, CATEGORY_DELETE_EXCEPTION_MESSAGE);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
