package com.nhnacademy.shoppingmall.entity.point;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
public class PointDetail {
    public enum Type {
        POINT_USE, POINT_SAVE
    }

    private int pointDetailID;
    private int volume;
    private Type type;
    private LocalDateTime createdAt;
    private String uniqueUserID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointDetail that = (PointDetail) o;
        return pointDetailID == that.pointDetailID && volume == that.volume && type == that.type && Objects.equals(createdAt, that.createdAt) && Objects.equals(uniqueUserID, that.uniqueUserID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointDetailID, volume, type, createdAt, uniqueUserID);
    }

    @Override
    public String toString() {
        return "PointDetail{" +
                "pointDetailID=" + pointDetailID +
                ", volume=" + volume +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", uniqueUserID='" + uniqueUserID + '\'' +
                '}';
    }
}
