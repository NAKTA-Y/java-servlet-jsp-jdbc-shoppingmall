package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/mypage/address_create.do", method = RequestMapping.Method.GET)
public class AddressCreateController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("type", "create");
        req.setAttribute("action", "/mypage/address_create.do");

        return "/shop/mypage/address_edit";
    }
}
