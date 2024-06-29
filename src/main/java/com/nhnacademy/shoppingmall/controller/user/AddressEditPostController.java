package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.exception.page.InvalidParameterException;
import com.nhnacademy.shoppingmall.entity.user.Address;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.user.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.service.user.AddressService;
import com.nhnacademy.shoppingmall.service.user.impl.AddressServiceImpl;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RequestMapping(value = "/mypage/address_edit.do", method = RequestMapping.Method.POST)
public class AddressEditPostController implements BaseController {
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        try {
            int addressId = Integer.parseInt(req.getParameter("address_id"));
            String name = req.getParameter("name");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");
            String addressDetail = req.getParameter("address_detail");
            String zipcode = req.getParameter("zipcode");
            String request = req.getParameter("request");
            String isDefault = req.getParameter("default");

            if (ObjectUtils.anyNull(name, phone, address, addressDetail, zipcode, request))
                throw InvalidParameterException.nullValueException();

            Address updatedAddress = addressService.getAddressByID(addressId);
            updatedAddress.updateAdressInfo(address, addressDetail, zipcode, name, phone, request);

            addressService.updateAddressInfo(updatedAddress);
            if (Objects.isNull(isDefault)) addressService.unSetDefaultAddress(addressId);
            else addressService.setDefaultAddress(user.getUniqueUserID(), addressId);

        } catch (NumberFormatException e) {
            throw InvalidParameterException.numberFormatException();
        }

        return "redirect:/mypage/address_list.do";
    }
}
