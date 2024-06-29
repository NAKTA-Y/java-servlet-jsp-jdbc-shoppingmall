package com.nhnacademy.shoppingmall.service.product.impl;

import com.nhnacademy.shoppingmall.entity.product.ProductImage;
import com.nhnacademy.shoppingmall.exception.product.ProductImageNoEffectException;
import com.nhnacademy.shoppingmall.exception.product.ProductImageNotFoundException;
import com.nhnacademy.shoppingmall.repository.product.ProductImageRepository;
import com.nhnacademy.shoppingmall.service.product.ProductImageService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ProductImageServiceImpl implements ProductImageService {
    private static final String PRODUCT_IMAGE_SAVE_EXCEPTION_MESSAGE = "상품 이미지를 추가하지 못했습니다.";
    private static final String PRODUCT_IMAGE_DELETE_EXCEPTION_MESSAGE = "상품 이미지를 삭제하지 못했습니다.";
    private static final String PRODUCT_IMAGE_NOT_FOUND_EXCEPTION_MESSAGE = "상품 이미지를 찾지 못했습니다.";

    private final ProductImageRepository productImageRepository;

    public ProductImageServiceImpl(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public int saveProductImage(ProductImage productImage) {
        int pk = productImageRepository.save(productImage);
        if (pk < 1) throw new ProductImageNoEffectException(HttpServletResponse.SC_NO_CONTENT, PRODUCT_IMAGE_SAVE_EXCEPTION_MESSAGE);
        return pk;
    }

    @Override
    public void deleteByID(int productImageID) {
        int result = productImageRepository.deleteByID(productImageID);
        if (result < 1) throw new ProductImageNoEffectException(HttpServletResponse.SC_NO_CONTENT, PRODUCT_IMAGE_DELETE_EXCEPTION_MESSAGE);
    }

    @Override
    public ProductImage getProductImageByID(int productImageID) {
        Optional<ProductImage> productImageOptional = productImageRepository.getProductImageByID(productImageID);
        if (productImageOptional.isEmpty()) throw new ProductImageNotFoundException(HttpServletResponse.SC_NOT_FOUND,PRODUCT_IMAGE_NOT_FOUND_EXCEPTION_MESSAGE);
        return productImageOptional.get();
    }

    @Override
    public List<ProductImage> getProductImagesByProductID(int productID) {
        return productImageRepository.getProductImagesByProductID(productID);
    }
}
