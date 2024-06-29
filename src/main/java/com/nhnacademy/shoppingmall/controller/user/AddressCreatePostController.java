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

@RequestMapping(value = "/mypage/address_create.do", method = RequestMapping.Method.POST)
public class AddressCreatePostController implements BaseController {
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String addressDetail = req.getParameter("address_detail");
        String zipcode = req.getParameter("zipcode");
        String request = req.getParameter("request");
        boolean isDefault = Objects.isNull(req.getParameter("default")) ? false : true;

        if (ObjectUtils.anyNull(name, phone, address, addressDetail, zipcode, request))
            throw InvalidParameterException.nullValueException();

        Address newAddress = Address.builder()
                .address(address)
                .addressDetail(addressDetail)
                .zipcode(zipcode)
                .name(name)
                .telephone(phone)
                .request(request)
                .defaultAddress(isDefault)
                .uniqueUserID(user.getUniqueUserID())
                .build();

        int addressID = addressService.saveAddress(newAddress);
        if (isDefault)
            addressService.setDefaultAddress(user.getUniqueUserID(), addressID);

        return "redirect:/mypage/address_list.do";
    }
}
