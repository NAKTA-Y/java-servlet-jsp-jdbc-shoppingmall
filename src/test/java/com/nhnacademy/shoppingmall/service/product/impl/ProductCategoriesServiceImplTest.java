package com.nhnacademy.shoppingmall.service.product.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.repository.product.ProductCategoriesRepository;
import com.nhnacademy.shoppingmall.service.product.ProductCategoriesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

class ProductCategoriesServiceImplTest {
    ProductCategoriesRepository productCategoriesRepository = Mockito.mock(ProductCategoriesRepository.class);
    ProductCategoriesService productCategoriesService = new ProductCategoriesServiceImpl(productCategoriesRepository);

    @Test
    @DisplayName("save")
    void save() {
        Mockito.when(productCategoriesRepository.save(anyInt(), anyInt())).thenReturn(1);
        Mockito.when(productCategoriesRepository.isExist(anyInt(), anyInt())).thenReturn(false);

        productCategoriesService.saveProductCategory(1, 1);

        Mockito.verify(productCategoriesRepository, Mockito.times(1)).save(anyInt(), anyInt());
    }

    @Test
    @DisplayName("delete")
    void delete() {
        Mockito.when(productCategoriesRepository.delete(anyInt(), anyInt())).thenReturn(1);
        Mockito.when(productCategoriesRepository.isExist(anyInt(), anyInt())).thenReturn(true);

        productCategoriesService.deleteProductCategory(1, 1);

        Mockito.verify(productCategoriesRepository, Mockito.times(1)).delete(anyInt(), anyInt());
        Mockito.verify(productCategoriesRepository, Mockito.times(1)).isExist(anyInt(), anyInt());
    }

    @Test
    @DisplayName("get product list")
    void get_product_list() {
        List<Product> products = new ArrayList<>();
        Page<Product> productPage = new Page<>(products, 0);

        Mockito.when(productCategoriesRepository.findProductsByCategoryID(anyInt(), anyInt(), anyInt())).thenReturn(productPage);
        productCategoriesService.getProductsByCategoryID(1, 1, 10);
        Mockito.verify(productCategoriesRepository, Mockito.times(1)).findProductsByCategoryID(anyInt(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("get category list")
    void get_category_list() {
        List<Category> categories = new ArrayList<>();
        Mockito.when(productCategoriesRepository.findCategoriesByProductID(anyInt())).thenReturn(categories);
        productCategoriesService.getCategoriesByProductID(1);
        Mockito.verify(productCategoriesRepository, Mockito.times(1)).findCategoriesByProductID(anyInt());
    }
}