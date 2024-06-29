package com.nhnacademy.shoppingmall.repository.product;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.entity.product.Product;

import java.util.List;

public interface ProductCategoriesRepository {
    int save(int productID, int categoryID);
    int delete(int productID, int categoryID);
    List<Category> findCategoriesByProductID(int productID);
    Page<Product> findProductsByCategoryID(int categoryID, int page, int pageSize);
    boolean isExist(int productID, int categoryID);
}
