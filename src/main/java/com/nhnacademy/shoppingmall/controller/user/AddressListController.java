package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.entity.user.Address;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.user.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.service.user.AddressService;
import com.nhnacademy.shoppingmall.service.user.impl.AddressServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping(value = "/mypage/address_list.do", method = RequestMapping.Method.GET)
public class AddressListController implements BaseController {
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        List<Address> addresses = addressService.getAddressesByUserID(user.getUniqueUserID());

        req.setAttribute("addresses", addresses);
        return "/shop/mypage/address_list";
    }
}
