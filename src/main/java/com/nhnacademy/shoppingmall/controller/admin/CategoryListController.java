package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.repository.product.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.service.product.CategoryService;
import com.nhnacademy.shoppingmall.service.product.impl.CategoryServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(value = "/admin/category_list.do", method = RequestMapping.Method.GET)
public class CategoryListController implements BaseController {
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Category> categories = categoryService.findAll();
        req.setAttribute("categories", categories);

        return "/shop/admin/category_list";
    }
}
