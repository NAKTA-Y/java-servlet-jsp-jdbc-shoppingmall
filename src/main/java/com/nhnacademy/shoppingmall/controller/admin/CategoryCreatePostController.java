package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.repository.product.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.service.product.CategoryService;
import com.nhnacademy.shoppingmall.service.product.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@RequestMapping(value = "/admin/category_create.do", method = RequestMapping.Method.POST)
public class CategoryCreatePostController implements BaseController {
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String categoryName = req.getParameter("category_name");

        if (Objects.isNull(categoryName)) {
            resp.setStatus(400);
            throw new RuntimeException("BAD REQUEST");
        }

        Category newCategory = new Category(categoryName);
        categoryService.saveCategory(newCategory);

        return "redirect:/admin/category_list.do";
    }
}
