package com.nhnacademy.shoppingmall.repository.point.impl;

import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseReadOperationException;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.point.PointDetail;
import com.nhnacademy.shoppingmall.repository.point.PointDetailRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class PointDetailRepositoryImpl implements PointDetailRepository {
    @Override
    public int savePointDetail(PointDetail pointDetail) {
        String sql = "INSERT INTO PointDetails VALUES (null, ?, ?, ?, ?)";

        int id = 0;
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            psmt.setInt(1, pointDetail.getVolume());
            psmt.setString(2, pointDetail.getType().name());
            psmt.setTimestamp(3, Objects.nonNull(pointDetail.getCreatedAt()) ? Timestamp.valueOf(pointDetail.getCreatedAt()) : null);
            psmt.setString(4, pointDetail.getUniqueUserID());

            psmt.executeUpdate();
            try (ResultSet rs = psmt.getGeneratedKeys()) {
                if (rs.next())
                    id = rs.getInt(1);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return id;
    }

    @Override
    public Page<PointDetail> getPointDetailPageByUserID(String uniqueUserID, int page, int pageSize) {
        int offset = (page-1) * pageSize;

        String sql = "SELECT * FROM PointDetails WHERE UniqueUserID = ? ORDER BY CreatedAt DESC LIMIT ?, ?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, uniqueUserID);
            psmt.setInt(2, offset);
            psmt.setInt(3, pageSize);

            List<PointDetail> pointDetails = new ArrayList<>();
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    pointDetails.add(
                            PointDetail.builder()
                                    .pointDetailID(rs.getInt("PointDetailID"))
                                    .volume(rs.getInt("Volume"))
                                    .type(PointDetail.Type.valueOf(rs.getString("Type")))
                                    .createdAt(Objects.nonNull(rs.getTimestamp("CreatedAt")) ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null)
                                    .uniqueUserID(rs.getString("UniqueUserID"))
                                    .build()
                    );
                }
            }

            long total = 0;

            if (!pointDetails.isEmpty())
                total = countAll(uniqueUserID);

            return new Page<>(pointDetails, total);

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public int getCurrentPointAmountByUserID(String uniqueUserID) {
        String sql = "SELECT SUM(Volume) as point FROM PointDetails WHERE UniqueUserID = ?";

        int point = 0;
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
           psmt.setString(1, uniqueUserID);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) point = rs.getInt("point");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }

        return point;
    }

    private int countAll(String uniqueUserID) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        String sql = "SELECT COUNT(*) as count FROM PointDetails WHERE UniqueUserID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        int count = 0;
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
