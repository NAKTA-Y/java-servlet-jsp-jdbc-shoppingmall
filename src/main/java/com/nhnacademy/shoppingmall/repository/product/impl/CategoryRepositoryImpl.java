package com.nhnacademy.shoppingmall.repository.product.impl;

import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseCreateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseDeleteOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseReadOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseUpdateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.entity.product.Category;
import com.nhnacademy.shoppingmall.repository.product.CategoryRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public int save(Category category) {
        String sql = "INSERT INTO Categories VALUES (null, ?)";

        int pk = 0;
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            psmt.setString(1, category.getCategoryName());

            psmt.executeUpdate();
            try (ResultSet rs = psmt.getGeneratedKeys()) {
                if (rs.next()) pk = rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseCreateOperationException();
        }

        return pk;
    }

    @Override
    public int update(Category category) {
        String sql = "UPDATE Categories SET CategoryName = ? WHERE CategoryID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, category.getCategoryName());
            psmt.setInt(2, category.getCategoryID());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public Optional<Category> getByID(int categoryID) {
        String sql = "SELECT * FROM Categories WHERE CategoryID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        Optional<Category> categoryOptional = Optional.empty();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryID);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    categoryOptional = Optional.of(
                            new Category(
                                    rs.getInt("CategoryID"),
                                    rs.getString("CategoryName")
                            )
                    );
                }
            }

            return categoryOptional;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public int deleteByID(int id) {
        String sql = "DELETE FROM Categories WHERE CategoryID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, id);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseDeleteOperationException();
        }
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM Categories ORDER BY CategoryID";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

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
}
