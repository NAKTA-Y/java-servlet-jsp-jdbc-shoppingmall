package com.nhnacademy.shoppingmall.repository.product.impl;

import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseCreateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseDeleteOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseReadOperationException;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.entity.product.ProductImage;
import com.nhnacademy.shoppingmall.repository.product.ProductImageRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class ProductImageRepositoryImpl implements ProductImageRepository {
    @Override
    public int save(ProductImage productImage) {
        String sql = "INSERT INTO ProductImages VALUES (null, ?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        int pk = 0;
        try (PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            psmt.setString(1, productImage.getProductImage());
            psmt.setInt(2, productImage.getImageSize());
            psmt.setInt(3, productImage.getProductID());

            psmt.executeUpdate();
            try (ResultSet rs = psmt.getGeneratedKeys()) {
                if (rs.next())
                    pk = rs.getInt(1);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseCreateOperationException();
        }

        return pk;
    }

    @Override
    public int deleteByID(int productImageID) {
        String sql = "DELETE FROM ProductImages WHERE ProductImageID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productImageID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseDeleteOperationException();
        }
    }

    @Override
    public Optional<ProductImage> getProductImageByID(int productImageID) {
        String sql = "SELECT * FROM ProductImages WHERE ProductImageID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        Optional<ProductImage> productImageOptional = Optional.empty();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productImageID);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    productImageOptional = Optional.of(ProductImage.builder()
                            .productImageID(rs.getInt("ProductImageID"))
                            .productImage(rs.getString("ProductImage"))
                            .imageSize(rs.getInt("ImageSize"))
                            .productID(rs.getInt("ProductID"))
                            .build());
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }

        return productImageOptional;
    }

    @Override
    public List<ProductImage> getProductImagesByProductID(int productID) {
        String sql = "SELECT * FROM ProductImages WHERE ProductID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productID);

            List<ProductImage> productImages = new ArrayList<>();
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    productImages.add(
                            ProductImage.builder()
                                    .productImageID(rs.getInt("ProductImageID"))
                                    .productImage(rs.getString("ProductImage"))
                                    .imageSize(rs.getInt("ImageSize"))
                                    .productID(rs.getInt("ProductID"))
                                    .build()
                    );
                }
            }

            return productImages;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }
}
