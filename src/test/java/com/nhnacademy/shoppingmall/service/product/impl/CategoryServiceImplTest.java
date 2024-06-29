package com.nhnacademy.shoppingmall.service.product.impl;

import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.repository.product.CategoryRepository;
import com.nhnacademy.shoppingmall.service.product.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

class CategoryServiceImplTest {
    CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    CategoryService categoryService = new CategoryServiceImpl(categoryRepository);

    Category testCategory = new Category(2, "testCategoryName");

    @Test
    @DisplayName("[성공] 카테고리 등록")
    void save() {
        Mockito.when(categoryRepository.save(any())).thenReturn(3);
        categoryService.saveCategory(testCategory);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("[실패] null 등록시 Exception 발생")
    void save_null() {
        assertThrows(RuntimeException.class, () -> categoryService.saveCategory(null));
    }

    @Test
    @DisplayName("[성공] 카테고리 조회")
    void getByID() {
        Mockito.when(categoryRepository.getByID(anyInt())).thenReturn(Optional.of(testCategory));
        Category findCategory = categoryService.getCategory(testCategory.getCategoryID());
        Mockito.verify(categoryRepository, Mockito.times(1)).getByID(anyInt());
        assertEquals(testCategory, findCategory);
    }

    @Test
    @DisplayName("[실패] 카테고리 조회 실패시 null 반환")
    void getByID_fail() {
        Mockito.when(categoryRepository.getByID(anyInt())).thenReturn(Optional.empty());
        Category findCategory = categoryService.getCategory(4);
        Mockito.verify(categoryRepository, Mockito.times(1)).getByID(anyInt());
        assertNull(findCategory);
    }

    @Test
    @DisplayName("[성공] 카테고리 수정")
    void update() {
        Mockito.when(categoryRepository.update(any())).thenReturn(1);
        categoryService.updateCategory(testCategory);
        Mockito.verify(categoryRepository, Mockito.times(1)).update(any());
    }

    @Test
    @DisplayName("[실패] 카테고리 수정시 이름이 50자가 넘어가면 Exception 발생")
    void update_fail_over_character_length() {
        testCategory.updateCategoryName("lllllllllllllllllllllllllllllllllllllllllllllllllll");
        Assertions.assertThrows(RuntimeException.class, () -> categoryService.updateCategory(testCategory));
    }

    @Test
    @DisplayName("[실패] 카테고리 수정시 null 이면 Exception 발생")
    void update_null() {
        Assertions.assertThrows(RuntimeException.class, () -> categoryService.updateCategory(null));
    }

    @Test
    @DisplayName("[성공] 카테고리 삭제")
    void delete() {
        Mockito.when(categoryRepository.deleteByID(anyInt())).thenReturn(1);
        categoryService.deleteCategory(testCategory.getCategoryID());
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteByID(anyInt());
    }

    @Test
    @DisplayName("[실패] 잘못된 ID로 카테고리 삭제시 Exception 발생")
    void delete_false_id() {
        Mockito.when(categoryRepository.deleteByID(anyInt())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> categoryService.deleteCategory(4));
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteByID(anyInt());
    }

    @Test
    @DisplayName("[성공] 카테고리 모두 가져오기")
    void pagination() {
        List<Category> categories = new ArrayList<>();
        categories.add(testCategory);

        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        categoryService.findAll();
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();
    }
}