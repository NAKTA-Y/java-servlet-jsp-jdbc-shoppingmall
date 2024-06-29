package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.InvalidParameterException;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.UnAuthorizedException;
import com.nhnacademy.shoppingmall.entity.user.Address;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.user.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.service.user.AddressService;
import com.nhnacademy.shoppingmall.service.user.impl.AddressServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RequestMapping(value = "/mypage/address_edit.do", method = RequestMapping.Method.GET)
public class AddressEditController implements BaseController {
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            int addressId = Integer.parseInt(req.getParameter("address_id"));

            Address address = addressService.getAddressByID(addressId);
            User user = (User) session.getAttribute("user");

            if (!Objects.equals(user.getUniqueUserID(), address.getUniqueUserID()))
                throw new UnAuthorizedException();

            req.setAttribute("address", address);
            req.setAttribute("address_id", addressId);
            req.setAttribute("action", "/mypage/address_edit.do");
        } catch (NumberFormatException e) {
            throw InvalidParameterException.numberFormatException();
        }

        return "/shop/mypage/address_edit";
    }
}
