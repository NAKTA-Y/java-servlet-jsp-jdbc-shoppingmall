package com.nhnacademy.shoppingmall.service.user.impl;

import com.nhnacademy.shoppingmall.entity.user.Address;
import com.nhnacademy.shoppingmall.exception.user.AddressNoEffectException;
import com.nhnacademy.shoppingmall.exception.user.AddressNotFoundException;
import com.nhnacademy.shoppingmall.repository.user.AddressRepository;
import com.nhnacademy.shoppingmall.service.user.AddressService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class AddressServiceImpl implements AddressService {
    private static final String ADDRESS_SAVE_EXCEPTION_MESSAGE = "주소를 생성하지 못했습니다.";
    private static final String ADDRESS_DELETE_EXCEPTION_MESSAGE = "주소를 삭제하지 못했습니다.";
    private static final String ADDRESS_UPDATE_EXCEPTION_MESSAGE = "주소 업데이트를 실패했습니다.";
    private static final String ADDRESS_NOT_FOUND_EXCEPTION_MESSAGE = "주소를 찾지 못했습니다.";

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public int saveAddress(Address address) {
        int pk = addressRepository.saveAddress(address);
        if (pk < 1)
            throw new AddressNoEffectException(HttpServletResponse.SC_NO_CONTENT, ADDRESS_SAVE_EXCEPTION_MESSAGE);

        return pk;
    }

    @Override
    public void deleteAddressByID(int addressID) {
        int result = addressRepository.deleteAddressByID(addressID);
        if (result < 1)
            throw new AddressNoEffectException(HttpServletResponse.SC_NO_CONTENT, ADDRESS_DELETE_EXCEPTION_MESSAGE);
    }

    @Override
    public void updateAddressInfo(Address address) {
        int result = addressRepository.updateAddressInfo(address);
        if (result < 1)
        throw new AddressNoEffectException(HttpServletResponse.SC_NO_CONTENT, ADDRESS_UPDATE_EXCEPTION_MESSAGE);
    }

    @Override
    public void setDefaultAddress(String uniqueUserID, int addressID) {
        List<Address> addresses = addressRepository.getAddressesByUserID(uniqueUserID);
        for (Address address : addresses)
            addressRepository.setDefaultAddress(address.getAddressID(), false);

        int result = addressRepository.setDefaultAddress(addressID, true);
        if (result < 1)
            throw new AddressNoEffectException(HttpServletResponse.SC_NO_CONTENT, ADDRESS_UPDATE_EXCEPTION_MESSAGE);
    }

    @Override
    public void unSetDefaultAddress(int addressID) {
        int result = addressRepository.setDefaultAddress(addressID, false);
        if (result < 1)
            throw new AddressNoEffectException(HttpServletResponse.SC_NO_CONTENT, ADDRESS_UPDATE_EXCEPTION_MESSAGE);
    }

    @Override
    public Address getAddressByID(int addressID) {
        Optional<Address> addressOptional = addressRepository.getAddressByID(addressID);
        if (addressOptional.isEmpty())
            throw new AddressNotFoundException(HttpServletResponse.SC_NOT_FOUND, ADDRESS_NOT_FOUND_EXCEPTION_MESSAGE);

        return addressOptional.get();
    }

    @Override
    public List<Address> getAddressesByUserID(String uniqueUserID) {
        return addressRepository.getAddressesByUserID(uniqueUserID);
    }
}
