package com.nhnacademy.shoppingmall.repository.order.impl;

import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseCreateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseReadOperationException;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.entity.order.OrderDetail;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.repository.order.OrderDetailRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class OrderDetailRepositoryImpl implements OrderDetailRepository {
    @Override
    public int saveOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO OrderDetails VALUES (?, ?, ?, ?)";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1, orderDetail.getOrderID());
            psmt.setInt(2, orderDetail.getProductID());
            psmt.setInt(3, orderDetail.getQuantity());
            psmt.setInt(4, orderDetail.getUnitPrice());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseCreateOperationException();
        }
    }

    @Override
    public List<Product> getProductsByOrderID(int orderID) {
        String sql = "SELECT * " +
                "FROM Products p " +
                "INNER JOIN (" +
                    "SELECT ProductID " +
                    "FROM OrderDetails " +
                    "WHERE OrderID = ?" +
                ") as od " +
                "ON p.ProductID = od.ProductID";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, orderID);

            List<Product> products = new ArrayList<>();
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    products.add(
                            Product.builder()
                                    .productID(rs.getInt("p.ProductID"))
                                    .modelNumber(rs.getString("ModelNumber"))
                                    .modelName(rs.getString("ModelName"))
                                    .banner(rs.getString("banner"))
                                    .thumbnail(rs.getString("Thumbnail"))
                                    .price(rs.getInt("Price"))
                                    .discountRate(rs.getInt("DiscountRate"))
                                    .sale(rs.getInt("Sale"))
                                    .description(rs.getString("Description"))
                                    .stock(rs.getInt("Stock"))
                                    .viewCount(rs.getInt("ViewCount"))
                                    .orderCount(rs.getInt("OrderCount"))
                                    .brand(rs.getString("Brand"))
                                    .state(Product.State.valueOf(rs.getString("State")))
                                    .createdAt(Objects.nonNull(rs.getTimestamp("CreatedAt")) ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null)
                                    .deletedAt(Objects.nonNull(rs.getTimestamp("DeletedAt")) ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null)
                                    .build()
                    );
                }
            }

            return products;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderID(int orderID) {
        String sql = "SELECT * FROM OrderDetails WHERE OrderID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, orderID);

            List<OrderDetail> orderDetails = new ArrayList<>();
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    orderDetails.add(
                            OrderDetail.builder()
                                    .orderID(rs.getInt("OrderID"))
                                    .productID(rs.getInt("ProductID"))
                                    .quantity(rs.getInt("Quantity"))
                                    .unitPrice(rs.getInt("UnitPrice"))
                                    .build()
                    );
                }
            }

            return orderDetails;

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }
}
