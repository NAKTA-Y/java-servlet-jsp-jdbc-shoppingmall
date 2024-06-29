package com.nhnacademy.shoppingmall.service.product.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.exception.product.ProductNoEffectException;
import com.nhnacademy.shoppingmall.exception.product.ProductNotFoundException;
import com.nhnacademy.shoppingmall.repository.product.ProductRepository;
import com.nhnacademy.shoppingmall.service.product.ProductService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private static final String PRODUCT_UPDATE_EXCEPTION_MESSAGE = "상품 업데이트를 실패했습니다.";
    private static final String PRODUCT_DELETE_EXCEPTION_MESSAGE = "상품을 삭제하지 못했습니다.";
    private static final String PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE = "상품을 찾지 못했습니다.";

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public int saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProduct(int productID) {
        Optional<Product> productOptional = productRepository.getByID(productID);
        if (productOptional.isEmpty()) throw new ProductNotFoundException(HttpServletResponse.SC_NOT_FOUND, PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE);
        return productOptional.get();
    }

    @Override
    public void updateProductInfo(Product product) {
        int result = productRepository.update(product);
        if (result < 1)
            throw new ProductNoEffectException(HttpServletResponse.SC_NO_CONTENT, PRODUCT_UPDATE_EXCEPTION_MESSAGE);
    }

    @Override
    public void deleteProduct(int productID) {
        int result = productRepository.deleteByID(productID);
        if (result < 1)
            throw new ProductNoEffectException(HttpServletResponse.SC_NO_CONTENT, PRODUCT_DELETE_EXCEPTION_MESSAGE);
    }

    @Override
    public void increaseViewCount(int productID) {
        int result = productRepository.increaseViewCount(productID);
        if (result < 1)
            throw new ProductNoEffectException(HttpServletResponse.SC_NO_CONTENT, PRODUCT_UPDATE_EXCEPTION_MESSAGE);

    }

    @Override
    public void increaseOrderCount(int productID) {
        int result = productRepository.increaseOrderCount(productID);
        if (result < 1)
            throw new ProductNoEffectException(HttpServletResponse.SC_NO_CONTENT, PRODUCT_UPDATE_EXCEPTION_MESSAGE);
    }

    @Override
    public void decreaseStock(int productID, int quantity) {
        int result = productRepository.decreaseStock(productID, quantity);
        if (result < 1)
            throw new ProductNoEffectException(HttpServletResponse.SC_NO_CONTENT, PRODUCT_UPDATE_EXCEPTION_MESSAGE);
    }

    @Override
    public List<Product> getBanners() {
        return productRepository.getBanners();
    }

    @Override
    public Page<Product> getProductPage(int page, int pageSize) {
        return productRepository.findAll(page, pageSize);
    }

    @Override
    public List<Product> getTopViewProducts(int limit) {
        return productRepository.getTopViewProducts(limit);
    }

    @Override
    public List<Product> getProductsByIDList(List<Integer> idList) {
        return productRepository.findAllByIDList(idList);
    }

    @Override
    public Page<Product> getProductsByKeyword(String keyword, int page, int pageSize) {
        return productRepository.getProductByKeyword(keyword, page, pageSize);
    }
}
