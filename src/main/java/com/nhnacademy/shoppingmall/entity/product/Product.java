package com.nhnacademy.shoppingmall.entity.product;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
public class Product {
    public enum State {
        ACTIVE, DELETED
    }

    private int             productID;
    private String          modelNumber;
    private String          modelName;
    private String          banner;
    private String          thumbnail;
    private int             price;
    private int             discountRate;
    private int             sale;
    private String          description;
    private int             stock;
    private int             viewCount;
    private int             orderCount;
    private String          brand;
    private State           state;
    private LocalDateTime   createdAt;
    private LocalDateTime   deletedAt;

    public void updateProductInfo(String modelName, String modelNumber, String description, int stock, String brand) {
        this.modelName = modelName;
        this.modelNumber = modelNumber;
        this.description = description;
        this.stock = stock;
        this.brand = brand;
    }

    public void updateImage(String banner, String thumbnail) {
        this.banner = banner;
        this.thumbnail = thumbnail;
    }

     public void updatePrice(int price, int discountRate) {
        this.price = price;
        this.discountRate = discountRate;
        this.sale = (int) (price * (1 - (double)discountRate / 100));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return     Objects.equals(productID, product.productID)
                && Objects.equals(modelNumber, product.modelNumber)
                && Objects.equals(modelName, product.modelName)
                && Objects.equals(banner, product.banner)
                && Objects.equals(thumbnail, product.thumbnail)
                && Objects.equals(price, product.price)
                && Objects.equals(discountRate, product.discountRate)
                && Objects.equals(sale, product.sale)
                && Objects.equals(description, product.description)
                && Objects.equals(stock, product.stock)
                && Objects.equals(viewCount, product.viewCount)
                && Objects.equals(orderCount, product.orderCount)
                && Objects.equals(brand, product.brand)
                && Objects.equals(state, product.state)
                && Objects.equals(createdAt, product.createdAt)
                && Objects.equals(deletedAt, product.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, modelNumber, modelName, banner, thumbnail, price, discountRate, sale, deletedAt, stock, viewCount, orderCount, brand, stock, createdAt, deletedAt);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", modelNumber='" + modelNumber + '\'' +
                ", modelName='" + modelName + '\'' +
                ", banner='" + banner + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", price=" + price +
                ", sale=" + sale +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", viewCount=" + viewCount +
                ", orderCount=" + orderCount +
                ", brand='" + brand + '\'' +
                ", state=" + state +
                ", createdAt=" + createdAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
