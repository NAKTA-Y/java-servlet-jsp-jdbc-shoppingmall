package com.nhnacademy.shoppingmall.service.user.impl;

import com.nhnacademy.shoppingmall.entity.user.Address;
import com.nhnacademy.shoppingmall.repository.user.AddressRepository;
import com.nhnacademy.shoppingmall.service.user.AddressService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;

class AddressServiceImplTest {
    AddressRepository addressRepository = Mockito.mock(AddressRepository.class);
    AddressService addressService = new AddressServiceImpl(addressRepository);

    Address testAddress = Address.builder()
            .addressID(1)
            .address("testAddress")
            .addressDetail("testAddressDetail")
            .zipcode("123456")
            .name("testName")
            .telephone("010-1234-1234")
            .request("testRequest")
            .defaultAddress(false)
            .uniqueUserID("testUniqueUserID")
            .build();

    @Test
    @DisplayName("address save")
    void save_address() {
        Mockito.when(addressRepository.saveAddress(any())).thenReturn(1);
        addressService.saveAddress(testAddress);
        Mockito.verify(addressRepository, Mockito.times(1)).saveAddress(any());
    }

    @Test
    @DisplayName("address delete")
    void delete_address() {
        Mockito.when(addressRepository.deleteAddressByID(anyInt())).thenReturn(1);
        addressService.deleteAddressByID(testAddress.getAddressID());
        Mockito.verify(addressRepository, Mockito.times(1)).deleteAddressByID(anyInt());
    }

    @Test
    @DisplayName("address update")
    void saveAddress() {
        Mockito.when(addressRepository.updateAddressInfo(any())).thenReturn(1);
        addressService.updateAddressInfo(testAddress);
        Mockito.verify(addressRepository, Mockito.times(1)).updateAddressInfo(any());
    }

    @Test
    @DisplayName("set default address")
    void set_default_address() {
        Mockito.when(addressRepository.setDefaultAddress(anyInt(), anyBoolean())).thenReturn(1);
        addressService.setDefaultAddress("", testAddress.getAddressID());
        Mockito.verify(addressRepository, Mockito.times(1)).setDefaultAddress(anyInt(), anyBoolean());
    }

    @Test
    @DisplayName("unset default address")
    void unset_default_address() {
        Mockito.when(addressRepository.setDefaultAddress(anyInt(), anyBoolean())).thenReturn(1);
        addressService.unSetDefaultAddress(testAddress.getAddressID());
        Mockito.verify(addressRepository, Mockito.times(1)).setDefaultAddress(anyInt(), anyBoolean());
    }

    @Test
    @DisplayName("get address")
    void get_address() {
        Mockito.when(addressRepository.getAddressByID(anyInt())).thenReturn(any());
        Assertions.assertThrows(RuntimeException.class, () -> addressService.getAddressByID(testAddress.getAddressID()));
        Mockito.verify(addressRepository, Mockito.times(1)).getAddressByID(anyInt());
    }

    @Test
    @DisplayName("get addresses by user id")
    void get_addresses_by_user_id() {
        Mockito.when(addressRepository.getAddressesByUserID(any())).thenReturn(any());
        addressService.getAddressesByUserID(testAddress.getUniqueUserID());
        Mockito.verify(addressRepository, Mockito.times(1)).getAddressesByUserID(any());
    }
}