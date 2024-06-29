package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.user.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.service.user.UserService;
import com.nhnacademy.shoppingmall.service.user.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping(value = "/mypage/user_delete.do", method = RequestMapping.Method.GET)
public class UserDeleteController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        userService.deleteUser(user.getUniqueUserID());
        session.invalidate();

        return "redirect:/index.do";
    }
}
