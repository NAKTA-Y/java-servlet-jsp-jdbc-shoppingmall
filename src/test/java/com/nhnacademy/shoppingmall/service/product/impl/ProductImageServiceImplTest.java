package com.nhnacademy.shoppingmall.service.product.impl;

import com.nhnacademy.shoppingmall.entity.product.ProductImage;
import com.nhnacademy.shoppingmall.repository.product.ProductImageRepository;
import com.nhnacademy.shoppingmall.service.product.ProductImageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

class ProductImageServiceImplTest {
    ProductImageRepository productImageRepository = Mockito.mock(ProductImageRepository.class);
    ProductImageService productImageService = new ProductImageServiceImpl(productImageRepository);

    ProductImage testProductImage = ProductImage.builder()
            .productImageID(2)
            .productImage("/images")
            .imageSize(100)
            .productID(1)
            .build();

    @Test
    void saveProductImage() {
        Mockito.when(productImageRepository.save(any())).thenReturn(2);
        productImageService.saveProductImage(testProductImage);
        Mockito.verify(productImageRepository, Mockito.times(1)).save(any());
    }

    @Test
    void deleteByID() {
        Mockito.when(productImageRepository.deleteByID(anyInt())).thenReturn(1);
        productImageService.deleteByID(testProductImage.getProductImageID());
        Mockito.verify(productImageRepository, Mockito.times(1)).deleteByID(anyInt());
    }

    @Test
    void getProductImagesByProductID() {
        List<ProductImage> productImages = new ArrayList<>();
        Mockito.when(productImageRepository.getProductImagesByProductID(anyInt())).thenReturn(productImages);
        productImageRepository.getProductImagesByProductID(testProductImage.getProductID());
        Mockito.verify(productImageRepository, Mockito.times(1)).getProductImagesByProductID(anyInt());
    }
}