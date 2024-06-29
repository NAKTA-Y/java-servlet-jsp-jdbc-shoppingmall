package com.nhnacademy.shoppingmall.entity.product;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class ProductImage {
    private int productImageID;
    private String productImage;
    private int imageSize;
    private int productID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductImage that = (ProductImage) o;
        return productImageID == that.productImageID
                && imageSize == that.imageSize
                && productID == that.productID
                && Objects.equals(productImage, that.productImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productImageID, productImage, imageSize, productID);
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "productImageID=" + productImageID +
                ", productImage='" + productImage + '\'' +
                ", imageSize=" + imageSize +
                ", productID=" + productID +
                '}';
    }
}
