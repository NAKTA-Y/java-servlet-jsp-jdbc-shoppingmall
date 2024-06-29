package com.nhnacademy.shoppingmall.repository.user;

import com.nhnacademy.shoppingmall.entity.user.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    int saveAddress(Address address);
    Optional<Address> getAddressByID(int addressID);
    int updateAddressInfo(Address address);
    int setDefaultAddress(int addressID, boolean isDefault);
    int deleteAddressByID(int addressID);
    int countDefaultAddress(String uniqueUserID);
    int countAddressByUserID(String uniqueUserID);
    List<Address> getAddressesByUserID(String uniqueUserID);
}
