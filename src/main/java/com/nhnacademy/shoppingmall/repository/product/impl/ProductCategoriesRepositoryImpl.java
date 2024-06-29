package com.nhnacademy.shoppingmall.repository.product.impl;

import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseCreateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseDeleteOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseReadOperationException;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.repository.product.ProductCategoriesRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ProductCategoriesRepositoryImpl implements ProductCategoriesRepository {
    @Override
    public int save(int productID, int categoryID) {
        String sql = "INSERT INTO ProductCategories VALUES (?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productID);
            psmt.setInt(2, categoryID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseCreateOperationException();
        }
    }

    @Override
    public int delete(int productID, int categoryID) {
        String sql = "DELETE FROM ProductCategories WHERE ProductID = ? AND CategoryID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productID);
            psmt.setInt(2, categoryID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseDeleteOperationException();
        }
    }

    @Override
    public List<Category> findCategoriesByProductID(int productID) {
        String sql = "SELECT c.CategoryID, CategoryName " +
                "FROM Categories c " +
                "INNER JOIN (" +
                "SELECT CategoryID " +
                "FROM ProductCategories " +
                "WHERE ProductID = ?" +
                ") as pc " +
                "ON c.CategoryID = pc.CategoryID";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productID);

            ResultSet rs = psmt.executeQuery();
            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                categories.add(
                        new Category(
                                rs.getInt("CategoryID"),
                                rs.getString("CategoryName")
                        )
                );
            }

            return categories;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public Page<Product> findProductsByCategoryID(int categoryID, int page, int pageSize) {
        int offset = (page-1) * pageSize;

        String sql = "SELECT * " +
                "FROM Products p " +
                "INNER JOIN (" +
                    "SELECT ProductID " +
                    "FROM ProductCategories " +
                    "WHERE CategoryID = ?" +
                ") as pc " +
                "ON p.ProductID = pc.ProductID " +
                "WHERE STATE != 'DELETED' " +
                "LIMIT ?, ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryID);
            psmt.setInt(2, offset);
            psmt.setInt(3, pageSize);

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

            long total = 0;
            if (!products.isEmpty())
                total = countAll(categoryID);

            return new Page<>(products, total);

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    private int countAll(int categoryID) {
        String sql = "SELECT COUNT(*) as count " +
                "FROM Products p " +
                "INNER JOIN (" +
                    "SELECT ProductID " +
                    "FROM ProductCategories " +
                    "WHERE CategoryID = ?" +
                ") as pc " +
                "ON p.ProductID = pc.ProductID " +
                "WHERE STATE != 'DELETED'";
        Connection connection = DbConnectionThreadLocal.getConnection();

        int count = 0;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryID);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next())
                    return rs.getInt("count");
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }

        return count;
    }

    @Override
    public boolean isExist(int productID, int categoryID) {
        String sql = "SELECT * FROM ProductCategories WHERE ProductID = ? AND CategoryID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productID);
            psmt.setInt(2, categoryID);

            try (ResultSet rs = psmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }
}
