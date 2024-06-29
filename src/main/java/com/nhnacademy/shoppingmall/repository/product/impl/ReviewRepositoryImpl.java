package com.nhnacademy.shoppingmall.repository.product.impl;

import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseCreateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseDeleteOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseReadOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseUpdateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.Review;
import com.nhnacademy.shoppingmall.repository.product.ReviewRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ReviewRepositoryImpl implements ReviewRepository {

    @Override
    public int save(Review review) {
        String sql = "INSERT INTO Reviews VALUES (null, ?, ?, ?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        int pk = 0;
        try (PreparedStatement psmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            psmt.setInt(1, review.getRating());
            psmt.setString(2, review.getComments());
            psmt.setInt(3, review.getRecommend());
            psmt.setInt(4, review.getProductID());
            psmt.setString(5, review.getUserID());

            psmt.executeUpdate();
            try (ResultSet rs = psmt.getGeneratedKeys()) {
                if (rs.next())
                    pk = rs.getInt(1);
            }

            return pk;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseCreateOperationException();
        }
    }

    @Override
    public Optional<Review> getByID(int reviewID) {
        String sql = "SELECT * FROM Reviews WHERE ReviewID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        Optional<Review> optionalReview = Optional.empty();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, reviewID);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    optionalReview = Optional.of(
                            Review.builder()
                                    .reviewID(rs.getInt("ReviewID"))
                                    .rating(rs.getInt("Rating"))
                                    .comments(rs.getString("Comments"))
                                    .recommend(rs.getInt("Recommend"))
                                    .productID(rs.getInt("ProductID"))
                                    .userID(rs.getString("UniqueUserID"))
                                    .build()
                    );
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }

        return optionalReview;
    }

    @Override
    public int deleteByID(int id) {
        String sql = "DELETE FROM Reviews WHERE ReviewID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, id);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseDeleteOperationException();
        }
    }

    public int plusRecommendCount(int reviewID) {
        String sql = "UPDATE Reviews SET Recommend = Recommend + 1 WHERE ReviewID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, reviewID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public Page<Review> getReviewsByProductID(int productID, int page, int pageSize) {
        int offset = (page-1) * pageSize;

        String sql = "SELECT * FROM Reviews WHERE ProductID = ? ORDER BY ReviewID LIMIT ?, ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productID);
            psmt.setInt(2, offset);
            psmt.setInt(3, pageSize);

            List<Review> reviews = new ArrayList<>();
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(
                            Review.builder()
                                    .reviewID(rs.getInt("ReviewID"))
                                    .rating(rs.getInt("Rating"))
                                    .comments(rs.getString("Comments"))
                                    .recommend(rs.getInt("Recommend"))
                                    .productID(rs.getInt("ProductID"))
                                    .userID(rs.getString("UniqueUserID"))
                                    .build()
                    );
                }
            }

            long total = 0;
            if (!reviews.isEmpty())
                total = countAll(productID);

            return new Page<>(reviews, total);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    private int countAll(int productID) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        String sql = "SELECT COUNT(*) as count FROM Reviews WHERE ProductID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, productID);
            psmt.executeQuery();

            int count = 0;
            try (ResultSet rs = psmt.getResultSet()) {
                if (rs.next()) count = rs.getInt("count");
            }

            return count;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }
}
