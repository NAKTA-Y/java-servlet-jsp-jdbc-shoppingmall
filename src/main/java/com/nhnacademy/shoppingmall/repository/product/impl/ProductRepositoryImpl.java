package com.nhnacademy.shoppingmall.repository.product.impl;

import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseCreateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseDeleteOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseReadOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseUpdateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.repository.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public int save(Product product) {
        String sql = "INSERT INTO Products VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            psmt.setString(1, product.getModelNumber());
            psmt.setString(2, product.getModelName());
            psmt.setString(3, product.getBanner());
            psmt.setString(4, product.getThumbnail());
            psmt.setInt(5, product.getPrice());
            psmt.setInt(6, product.getDiscountRate());
            psmt.setInt(7, product.getSale());
            psmt.setString(8, product.getDescription());
            psmt.setInt(9, product.getStock());
            psmt.setInt(10, product.getViewCount());
            psmt.setInt(11, product.getOrderCount());
            psmt.setString(12, product.getBrand());
            psmt.setString(13, product.getState().name());
            psmt.setTimestamp(14, Objects.nonNull(product.getCreatedAt()) ? Timestamp.valueOf(product.getCreatedAt()) : null);
            psmt.setTimestamp(15, Objects.nonNull(product.getDeletedAt()) ? Timestamp.valueOf(product.getDeletedAt()) : null);

            psmt.executeUpdate();

            int pk = 0;
            try (ResultSet rs = psmt.getGeneratedKeys()) {
                if (rs.next())
                    pk = rs.getInt(1);

                return pk;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseCreateOperationException();
        }
    }

    @Override
    public Optional<Product> getByID(int productID) {
        String sql = "SELECT * " +
                     "FROM Products " +
                     "WHERE ProductID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productID);

            Optional<Product> foundProduct = Optional.empty();
            try (ResultSet rs = psmt.executeQuery()){
                if (rs.next()) foundProduct = Optional.of(createProduct(rs));
            }

            return foundProduct;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public int update(Product product) {
        String sql = "UPDATE Products " +
                "SET ModelNumber = ?, ModelName = ?, Banner = ?, Thumbnail = ?, " +
                "    Price = ?, DiscountRate = ?, Sale = ?, Description = ?, " +
                "    Stock = ?, Brand = ? " +
                "WHERE ProductID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, product.getModelNumber());
            psmt.setString(2, product.getModelName());
            psmt.setString(3, product.getBanner());
            psmt.setString(4, product.getThumbnail());
            psmt.setInt(5, product.getPrice());
            psmt.setInt(6, product.getDiscountRate());
            psmt.setInt(7, product.getSale());
            psmt.setString(8, product.getDescription());
            psmt.setInt(9, product.getStock());
            psmt.setString(10, product.getBrand());
            psmt.setInt(11, product.getProductID());

            return psmt.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public int deleteByID(int productID) {
        String sql = "UPDATE Products " +
                     "SET State = ?, DeletedAt = ?" +
                     "WHERE ProductID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, Product.State.DELETED.name());
            psmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            psmt.setInt(3, productID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseDeleteOperationException();
        }
    }

    @Override
    public int increaseViewCount(int productID) {
        String sql = "UPDATE Products " +
                "SET ViewCount = ViewCount + 1 " +
                "WHERE ProductID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public int increaseOrderCount(int productID) {
        String sql = "UPDATE Products " +
                "SET OrderCount = OrderCount + 1 " +
                "WHERE ProductID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public int decreaseStock(int productID, int quantity) {
        String sql = "UPDATE Products " +
                "SET Stock = Stock - ? " +
                "WHERE ProductID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, quantity);
            psmt.setInt(2, productID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) as count " +
                     "FROM Products " +
                     "WHERE State != 'DELETED' AND Stock > 0";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            if (rs.next()) return rs.getInt("count");
            else           return 0;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    private int countAllKeyword(String keyword) {
        String sql = "SELECT COUNT(*) as count " +
                     "FROM Products " +
                     "WHERE ModelName LIKE ? AND State != 'DELETED' AND Stock > 0";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
             psmt.setString(1, keyword);

             try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) return rs.getInt("count");
                else           return 0;
             }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public Page<Product> findAll(int page, int pageSize) {
        int offset = (page-1) * pageSize;

        String sql = "SELECT * FROM Products " +
                     "WHERE State != 'DELETED' AND Stock > 0 " +
                     "ORDER BY ProductID " +
                     "LIMIT ?, ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, offset);
            psmt.setInt(2, pageSize);

            try (ResultSet rs = psmt.executeQuery()) {
                List<Product> productList = new ArrayList<>();
                while (rs.next()) productList.add(createProduct(rs));

                long total;
                if (productList.isEmpty()) total = 0;
                else                       total = countAll();

                return new Page<>(productList, total);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public List<Product> getBanners() {
        String sql = "SELECT * " +
                "FROM Products " +
                "WHERE Banner IS NOT NULL AND State != 'DELETED' AND Stock > 0 " +
                "ORDER BY ViewCount " +
                "LIMIT 0, 10";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery()) {

            List<Product> productList = new ArrayList<>();
            while (rs.next()) productList.add(createProduct(rs));

            return productList;

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public List<Product> getTopViewProducts(int limit) {
        String sql = "SELECT * FROM Products  " +
                     "WHERE State != 'DELETED' AND Stock > 0 " +
                     "ORDER BY ViewCount DESC " +
                     "LIMIT 0, ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, limit);

            try (ResultSet rs = psmt.executeQuery()) {
                List<Product> products = new ArrayList<>();

                while (rs.next()) products.add(createProduct(rs));

                return products;
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public List<Product> findAllByIDList(List<Integer> idList) {
        List<Product> products = new ArrayList<>();

        if (idList.isEmpty()) return products;
        StringBuilder sql = new StringBuilder("SELECT * FROM Products WHERE ProductID IN (");
        for (int i = 0; i < idList.size(); i++) {
            sql.append("?");
            if (i < idList.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(")");

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < idList.size(); i++) {
                psmt.setInt(i+1, idList.get(i));
            }

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) products.add(createProduct(rs));
                return products;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public Page<Product> getProductByKeyword(String keyword, int page, int pageSize) {
        int offset = (page-1) * pageSize;
        int limit = pageSize;
        String pattern = "%" + keyword + "%";

        String sql = "SELECT * " +
                     "FROM Products " +
                     "WHERE ModelName LIKE ? AND State != 'DELETED' AND Stock > 0 " +
                     "ORDER BY ModelName " +
                     "LIMIT ?, ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, pattern);
            psmt.setInt(2, offset);
            psmt.setInt(3, limit);

            try (ResultSet rs = psmt.executeQuery()) {
                List<Product> products = new ArrayList<>();

                while (rs.next()) products.add(createProduct(rs));

                long total;
                if (products.isEmpty()) total = 0;
                else                    total = countAllKeyword(keyword);

                return new Page<>(products, total);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    private Product createProduct(ResultSet rs) throws SQLException {
        return Product.builder()
                .productID      (rs.getInt("ProductID"))
                .modelNumber    (rs.getString("ModelNumber"))
                .modelName      (rs.getString("ModelName"))
                .banner         (rs.getString("banner"))
                .thumbnail      (rs.getString("Thumbnail"))
                .price          (rs.getInt("Price"))
                .discountRate   (rs.getInt("DiscountRate"))
                .sale           (rs.getInt("Sale"))
                .description    (rs.getString("Description"))
                .stock          (rs.getInt("Stock"))
                .viewCount      (rs.getInt("ViewCount"))
                .orderCount     (rs.getInt("OrderCount"))
                .brand          (rs.getString("Brand"))
                .state          (Product.State.valueOf(rs.getString("State")))
                .createdAt      (Objects.nonNull(rs.getTimestamp("CreatedAt")) ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null)
                .deletedAt      (Objects.nonNull(rs.getTimestamp("DeletedAt")) ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null)
                .build();
    }
}
