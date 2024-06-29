package com.nhnacademy.shoppingmall.service.product.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.repository.product.ProductRepository;
import com.nhnacademy.shoppingmall.service.product.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

class ProductServiceImplTest {
    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ProductService productService = new ProductServiceImpl(productRepository);
    Product testProduct = Product.builder()
            .productID(2)
                .modelNumber("Number")
                .modelName("testModelName")
                .banner("banner")
                .thumbnail("thumbnail")
                .price(100)
                .sale(50)
                .description("description")
                .stock(1)
                .viewCount(1)
                .orderCount(1)
                .brand("brand")
                .state(Product.State.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

    @Test
    @DisplayName("save product")
    void saveProduct() {
        Mockito.when(productRepository.save(any())).thenReturn(1);
        productService.saveProduct(testProduct);
        Mockito.verify(productRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("get Product")
    void getProudct() {
        Mockito.when(productRepository.getByID(anyInt())).thenReturn(Optional.of(testProduct));
        productService.getProduct(testProduct.getProductID());
        Mockito.verify(productRepository, Mockito.times(1)).getByID(anyInt());
    }

    @Test
    @DisplayName("get Product not found")
    void getProudctNotFound() {
        Mockito.when(productRepository.getByID(anyInt())).thenReturn(Optional.empty());
        Product product = productService.getProduct(123);
        Mockito.verify(productRepository, Mockito.times(1)).getByID(anyInt());
        Assertions.assertNull(product);
    }

    @Test
    @DisplayName("update product info")
    void updateProductInfo() {
        Mockito.when(productRepository.update(any())).thenReturn(1);
        productService.updateProductInfo(testProduct);
        Mockito.verify(productRepository, Mockito.times(1)).update(any());
    }

    @Test
    @DisplayName("delete product")
    void deleteProduct() {
        Mockito.when(productRepository.deleteByID(anyInt())).thenReturn(1);
        productService.deleteProduct(testProduct.getProductID());
        Mockito.verify(productRepository, Mockito.times(1)).deleteByID(anyInt());
    }

    @Test
    @DisplayName("get banners")
    void getBanners() {
        List<Product> products = new ArrayList<>();
        products.add(testProduct);

        Mockito.when(productRepository.getBanners()).thenReturn(products);
        productService.getBanners();
        Mockito.verify(productRepository, Mockito.times(1)).getBanners();
    }

    @Test
    @DisplayName("pagination")
    void pagination() {
        List<Product> products = new ArrayList<>();
        products.add(testProduct);

        Mockito.when(productRepository.findAll(anyInt(), anyInt())).thenReturn(new Page<>(products, 1));
        productService.getProductPage(1, 10);
        Mockito.verify(productRepository, Mockito.times(1)).findAll(anyInt(), anyInt());
    }

    @Test
    @DisplayName("get top view products")
    void get_top_view_products() {
        List<Product> products = new ArrayList<>();
        products.add(testProduct);

        Mockito.when(productRepository.getTopViewProducts(anyInt())).thenReturn(products);
        productService.getTopViewProducts(10);
        Mockito.verify(productRepository, Mockito.times(1)).getTopViewProducts(anyInt());
    }

    @Test
    @DisplayName("get products by id list")
    void get_products_by_id_list() {
        List<Integer> idList = new ArrayList<>();
        idList.add(2);

        Mockito.when(productRepository.findAllByIDList(any())).thenReturn(new ArrayList<>());
        productService.getProductsByIDList(idList);
        Mockito.verify(productRepository, Mockito.times(1)).findAllByIDList(any());
    }

    @Test
    @DisplayName("get products by keyword")
    void get_products_by_keyword() {
        Mockito.when(productRepository.getProductByKeyword(anyString(), anyInt(), anyInt())).thenReturn(new Page<>(new ArrayList<>(), 0));
        productService.getProductsByKeyword("test", 1, 10);
        Mockito.verify(productRepository, Mockito.times(1)).getProductByKeyword(anyString(), anyInt(), anyInt());
    }

}