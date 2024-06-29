package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.repository.product.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.service.product.CategoryService;
import com.nhnacademy.shoppingmall.service.product.impl.CategoryServiceImpl;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/admin/category_update.do", method = RequestMapping.Method.POST)
public class CategoryUpdateController implements BaseController {
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId = req.getParameter("category_id");
        String categoryName = req.getParameter("category_name");

        if (ObjectUtils.anyNull(categoryId, categoryName)) {
            resp.setStatus(400);
            throw new RuntimeException("BAD REQUEST");
        }

        Category category = categoryService.getCategory(Integer.valueOf(categoryId));
        category.updateCategoryName(categoryName);

        categoryService.updateCategory(category);

        return "redirect:/admin/category_list.do";
    }
}
