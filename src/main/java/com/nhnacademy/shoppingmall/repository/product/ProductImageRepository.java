package com.nhnacademy.shoppingmall.repository.product;

import com.nhnacademy.shoppingmall.entity.product.ProductImage;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository {
    int save(ProductImage productImage);
    int deleteByID(int productImageID);
    Optional<ProductImage> getProductImageByID(int productImageID);
    List<ProductImage> getProductImagesByProductID(int productID);
}
