package com.nhnacademy.shoppingmall.service.user;

import com.nhnacademy.shoppingmall.entity.user.Address;

import java.util.List;

public interface AddressService {
    int saveAddress(Address address);
    void deleteAddressByID(int addressID);
    void updateAddressInfo(Address address);
    void setDefaultAddress(String uniqueUserID, int addressID);
    void unSetDefaultAddress(int addressID);
    Address getAddressByID(int addressID);
    List<Address> getAddressesByUserID(String uniqueUserID);
}