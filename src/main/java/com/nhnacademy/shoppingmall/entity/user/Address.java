package com.nhnacademy.shoppingmall.entity.user;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class Address {
    private int addressID;
    private String address;
    private String addressDetail;
    private String zipcode;
    private String name;
    private String telephone;
    private String request;
    private boolean defaultAddress;
    private String uniqueUserID;

    public void updateAdressInfo(
            String address,
            String addressDetail,
            String zipcode,
            String name,
            String telephone,
            String request
    ) {
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipcode = zipcode;
        this.name = name;
        this.telephone = telephone;
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return addressID == address1.addressID && defaultAddress == address1.defaultAddress && Objects.equals(address, address1.address) && Objects.equals(addressDetail, address1.addressDetail) && Objects.equals(zipcode, address1.zipcode) && Objects.equals(name, address1.name) && Objects.equals(telephone, address1.telephone) && Objects.equals(request, address1.request) && Objects.equals(uniqueUserID, address1.uniqueUserID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressID, address, addressDetail, zipcode, name, telephone, request, defaultAddress, uniqueUserID);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressID=" + addressID +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", request='" + request + '\'' +
                ", defaultAddress=" + defaultAddress +
                ", uniqueUserID='" + uniqueUserID + '\'' +
                '}';
    }
}
