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

@RequestMapping(value = "/mypage/infoUpdate.do", method = RequestMapping.Method.POST)
public class PersonalInfoUpdateController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String name = req.getParameter("name");
        String birth = req.getParameter("birth");
        String phone = req.getParameter("phone");

        if (ObjectUtils.anyNull(name, birth, phone))
            throw InvalidParameterException.nullValueException();

        user.updateUserInfo(name, birth, phone);
        userService.updateUserInfo(user);
        session.setAttribute("user", user);

        return "redirect:/mypage/info.do";
    }
}
