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

@RequestMapping(value = "/mypage/address_delete.do", method = RequestMapping.Method.GET)
public class AddressDeleteController implements BaseController {
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            int addressId = Integer.parseInt(req.getParameter("address_id"));

            Address address = addressService.getAddressByID(addressId);
            if (!Objects.equals(address.getUniqueUserID(), user.getUniqueUserID()))
                throw new UnAuthorizedException();

            addressService.deleteAddressByID(addressId);
        } catch (NumberFormatException e) {
            throw InvalidParameterException.numberFormatException();
        }

        return "redirect:/mypage/address_list.do";
    }
}
