package com.nhnacademy.shoppingmall.service.product;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.entity.product.Product;

import java.util.List;

public interface ProductCategoriesService {
    void saveProductCategory(int productID, int categoryID);
    void deleteProductCategory(int productID, int categoryID);
    List<Category> getCategoriesByProductID(int productID);
    Page<Product> getProductsByCategoryID(int categoryID, int page, int pageSize);
    boolean isExist(int productID, int categoryID);
}
