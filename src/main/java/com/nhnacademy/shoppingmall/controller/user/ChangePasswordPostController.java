package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.InvalidParameterException;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.user.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.service.user.UserService;
import com.nhnacademy.shoppingmall.service.user.impl.UserServiceImpl;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping(value = "/mypage/change_password.do", method = RequestMapping.Method.POST)
public class ChangePasswordPostController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String currentPassword = req.getParameter("current_password");
        String newPassword = req.getParameter("new_password");

        if (ObjectUtils.anyNull(currentPassword, newPassword))
            throw InvalidParameterException.nullValueException();

        user.updateLoginInfo(user.getUserID(), newPassword);
        userService.updateLoginInfo(user);

        return "/shop/mypage/change_password";
    }
}
