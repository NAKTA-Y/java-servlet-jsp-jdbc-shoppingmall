package com.nhnacademy.shoppingmall.repository.order.impl;

import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseCreateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseReadOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseUpdateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.order.Order;
import com.nhnacademy.shoppingmall.repository.order.OrderRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Slf4j
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public int save(Order order) {
        String sql = "INSERT INTO Orders VALUES (null, ?, ?, ?, ?, ?)";

        int id = 0;
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)){
            psmt.setTimestamp(1, Objects.nonNull(order.getOrderDate()) ? Timestamp.valueOf(order.getOrderDate()) : null);
            psmt.setTimestamp(2, Objects.nonNull(order.getShipDate()) ? Timestamp.valueOf(order.getShipDate()) : null);
            psmt.setInt(3, order.getPrice());
            psmt.setString(4, order.getUniqueUserID());
            psmt.setInt(5, order.getAddressID());

            psmt.executeUpdate();
            try (ResultSet rs = psmt.getGeneratedKeys()) {
                if (rs.next())
                    id = rs.getInt(1);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseCreateOperationException();
        }

        return id;
    }

    @Override
    public Optional<Order> getByID(int id) {
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        Optional<Order> findOrder = Optional.empty();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, id);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next())
                    findOrder = Optional.of(createOrder(rs));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }

        return findOrder;
    }

    @Override
    public int updateShipDate(int orderID, LocalDateTime date) {
        String sql = "UPDATE Orders SET ShipDate = ? WHERE OrderID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setTimestamp(1, Objects.nonNull(date) ? Timestamp.valueOf(date) : null);
            psmt.setInt(2, orderID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public Page<Order> getOrderPageByUserID(String uniqueUserID, int page, int pageSize) {
        int offset = (page-1) * pageSize;

        String sql = "SELECT * FROM Orders WHERE UniqueUserID = ? ORDER BY OrderDate DESC LIMIT ?, ?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, uniqueUserID);
            psmt.setInt(2, offset);
            psmt.setInt(3, pageSize);

            List<Order> orders = new ArrayList<>();
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) orders.add(createOrder(rs));
            }

            long total = 0;
            if (!orders.isEmpty())
                total = countAll(uniqueUserID);

            return new Page<>(orders, total);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    private int countAll(String uniqueUserID) {
        String sql = "SELECT COUNT(*) as count FROM Orders WHERE UniqueUserID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        int count = 0;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, uniqueUserID);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next())
                    count = rs.getInt("count");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }

        return count;
    }

    private Order createOrder(ResultSet rs) throws SQLException {
        return Order.builder()
                .orderID(rs.getInt("OrderID"))
                .orderDate(Objects.nonNull(rs.getTimestamp("OrderDate")) ? rs.getTimestamp("OrderDate").toLocalDateTime() : null)
                .shipDate(Objects.nonNull(rs.getTimestamp("ShipDate")) ? rs.getTimestamp("ShipDate").toLocalDateTime() : null)
                .price(rs.getInt("Price"))
                .uniqueUserID(rs.getString("UniqueUserID"))
                .addressID(rs.getInt("AddressID"))
                .build();
    }
}
