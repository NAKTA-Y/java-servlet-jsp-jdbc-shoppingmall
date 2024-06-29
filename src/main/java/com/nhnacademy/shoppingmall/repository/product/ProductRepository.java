package com.nhnacademy.shoppingmall.repository.product;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    int save(Product product);
    Optional<Product> getByID(int productID);
    int update(Product product);
    int deleteByID(int productID);
    int increaseViewCount(int productID);
    int increaseOrderCount(int productID);
    int decreaseStock(int productID, int quantity);
    int countAll();
    Page<Product> findAll(int page, int pageSize);
    List<Product> getBanners();
    List<Product> getTopViewProducts(int limit);
    List<Product> findAllByIDList(List<Integer> idList);
    Page<Product> getProductByKeyword(String keyword, int page, int pageSize);
}
