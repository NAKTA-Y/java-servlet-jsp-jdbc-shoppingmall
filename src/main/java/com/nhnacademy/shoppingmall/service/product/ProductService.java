package com.nhnacademy.shoppingmall.service.product;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Product;

import java.util.List;

public interface ProductService {
    int saveProduct(Product product);
    Product getProduct(int productID);
    void updateProductInfo(Product product);
    void deleteProduct(int productID);
    void increaseViewCount(int productID);
    void increaseOrderCount(int productID);
    void decreaseStock(int productID, int quantity);
    List<Product> getBanners();
    Page<Product> getProductPage(int page, int pageSize);
    List<Product> getTopViewProducts(int limit);
    List<Product> getProductsByIDList(List<Integer> idList);
    Page<Product> getProductsByKeyword(String keyword, int page, int pageSize);
}
