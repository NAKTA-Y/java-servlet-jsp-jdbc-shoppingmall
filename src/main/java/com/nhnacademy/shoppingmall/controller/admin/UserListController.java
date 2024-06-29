package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.InvalidParameterException;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.page.Pagination;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.user.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.service.user.UserService;
import com.nhnacademy.shoppingmall.service.user.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@RequestMapping(value = "/admin/user_list.do", method = RequestMapping.Method.GET)
public class UserListController implements BaseController {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userPageParam = req.getParameter("user_page");
        String userPageSizeParam = req.getParameter("user_pageSize");
        String adminPageParam = req.getParameter("admin_page");
        String adminPageSizeParam = req.getParameter("admin_pageSize");

        try {
            int userPage = Objects.isNull(userPageParam) || userPageParam.isEmpty() ? DEFAULT_PAGE : Integer.parseInt(userPageParam);
            int userPageSize = Objects.isNull(userPageSizeParam) || userPageSizeParam.isEmpty() ? DEFAULT_PAGE_SIZE : Integer.parseInt(userPageSizeParam);

            Page<User> pageUser = userService.getUserPage(userPage, userPageSize);
            List<User> users = pageUser.getContent();

            req.setAttribute("users", users);
            req.setAttribute("userPage", userPage);
            req.setAttribute("userPageSize", userPageSize);
            req.setAttribute("userStartPage", Pagination.getStartPage(userPage));
            req.setAttribute("userLastPage", Pagination.getLastPage(pageUser.getTotalCount(), userPage, userPageSize));
            req.setAttribute("userTotalPage", Pagination.getTotalPage(pageUser.getTotalCount(), userPageSize));

            int adminPage = Objects.isNull(adminPageParam) || adminPageParam.isEmpty() ? DEFAULT_PAGE : Integer.parseInt(adminPageParam);
            int adminPageSize = Objects.isNull(adminPageSizeParam) || adminPageSizeParam.isEmpty() ? DEFAULT_PAGE_SIZE : Integer.parseInt(adminPageSizeParam);

            Page<User> pageAdmin = userService.getAdminPage(adminPage, adminPageSize);
            List<User> admins = pageAdmin.getContent();

            req.setAttribute("admins", admins);
            req.setAttribute("adminPage", adminPage);
            req.setAttribute("adminPageSize", adminPageSize);
            req.setAttribute("adminStartPage", Pagination.getStartPage(adminPage));
            req.setAttribute("adminLastPage", Pagination.getLastPage(pageAdmin.getTotalCount(), adminPage, adminPageSize));
            req.setAttribute("adminTotalPage", Pagination.getTotalPage(pageAdmin.getTotalCount(), adminPageSize));
        } catch (NumberFormatException e) {
            throw InvalidParameterException.numberFormatException();
        }

        return "/shop/admin/user_list";
    }
}
