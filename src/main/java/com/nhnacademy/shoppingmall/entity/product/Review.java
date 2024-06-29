package com.nhnacademy.shoppingmall.entity.product;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Builder
@Getter
public class Review {
    private int     reviewID;
    private int     rating;
    private String  comments;
    private int     recommend;
    private int     productID;
    private String  userID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;
        return  reviewID == review.reviewID
                && rating == review.rating
                && Objects.equals(comments, review.comments)
                && Objects.equals(recommend, review.recommend)
                && productID == review.productID
                && Objects.equals(userID, review.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewID, rating, comments, recommend, productID, userID);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", recommend=" + recommend +
                ", productID=" + productID +
                ", userID='" + userID + '\'' +
                '}';
    }
}
