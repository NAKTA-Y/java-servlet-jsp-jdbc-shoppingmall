package com.nhnacademy.shoppingmall.service.product;

import com.nhnacademy.shoppingmall.entity.product.ProductImage;

import java.util.List;

public interface ProductImageService {
    int saveProductImage(ProductImage productImage);
    void deleteByID(int productImageID);
    ProductImage getProductImageByID(int productImageID);
    List<ProductImage> getProductImagesByProductID(int productID);
}
