package com.nhnacademy.shoppingmall.repository.cart.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.entity.cart.ShoppingCart;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseCreateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseDeleteOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseReadOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseUpdateOperationException;
import com.nhnacademy.shoppingmall.repository.cart.ShoppingCartRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {
    @Override
    public int save(ShoppingCart shoppingCart) {
        String sql = "INSERT INTO ShoppingCart VALUES (?, ?, ?, ?)";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, shoppingCart.getUniqueUserID());
            psmt.setInt(2, shoppingCart.getProductID());
            psmt.setInt(3, shoppingCart.getQuantity());
            psmt.setTimestamp(4, Objects.nonNull(shoppingCart.getCreatedAt()) ? Timestamp.valueOf(shoppingCart.getCreatedAt()) : null);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseCreateOperationException();
        }
    }

    @Override
    public int deleteByUserIDAndProductID(String uniqueUserID, int productID) {
        String sql = "DELETE FROM ShoppingCart WHERE UniqueUserID = ? AND ProductID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, uniqueUserID);
            psmt.setInt(2, productID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseDeleteOperationException();
        }
    }

    @Override
    public int updateQuantity(String uniqueUserID, int productID, int quantity) {
        String sql = "UPDATE ShoppingCart SET Quantity = ? WHERE UniqueUserID = ? AND ProductID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, quantity);
            psmt.setString(2, uniqueUserID);
            psmt.setInt(3, productID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public List<ShoppingCart> getShoppingCartByUserID(String uniqueUserID) {
        String sql = "SELECT * FROM ShoppingCart WHERE UniqueUserID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, uniqueUserID);
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    shoppingCarts.add(
                            ShoppingCart.builder()
                                    .uniqueUserID(rs.getString("UniqueUserID"))
                                    .productID(rs.getInt("ProductID"))
                                    .quantity(rs.getInt("Quantity"))
                                    .createdAt(rs.getTimestamp("CreatedAt").toLocalDateTime())
                                    .build()
                    );
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }

        return shoppingCarts;
    }

    @Override
    public Optional<ShoppingCart> getCartByUserIDAndProductID(String uniqueUserID, int productID) {
        String sql = "SELECT * FROM ShoppingCart WHERE UniqueUserID = ? AND ProductID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        Optional<ShoppingCart> cartOptional = Optional.empty();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, uniqueUserID);
            psmt.setInt(2, productID);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next())
                    cartOptional = Optional.of(
                        ShoppingCart.builder()
                                .uniqueUserID(rs.getString("UniqueUserID"))
                                .productID(rs.getInt("ProductID"))
                                .quantity(rs.getInt("Quantity"))
                                .createdAt(rs.getTimestamp("CreatedAt").toLocalDateTime())
                                .build()
                    );
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }

        return cartOptional;
    }

    @Override
    public boolean isExist(String uniqueUserID, int productID) {
        String sql = "SELECT * FROM ShoppingCart WHERE UniqueUserID = ? AND ProductID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, uniqueUserID);
            psmt.setInt(2, productID);

            try (ResultSet rs = psmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public int countAllByUserID(String uniqueUserID) {
        String sql = "SELECT COUNT(*) as count FROM ShoppingCart WHERE UniqueUserID = ?";

        int count = 0;
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, uniqueUserID);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) count = rs.getInt("count");
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }

        return count;
    }
}
