package com.nhnacademy.shoppingmall.repository.user.impl;

import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseCreateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseDeleteOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseReadOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseUpdateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        /*todo#3-1 회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
          해당 코드는 SQL Injection이 발생합니다. SQL Injection이 발생하지 않도록 수정하세요.
         */
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Users WHERE UserID = ? AND UserPassword = ? AND State != 'DELETED'";

        log.debug("sql:{}", sql);

        Optional<User> userOptional = Optional.empty();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setString(2, userPassword);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) userOptional = Optional.of(createUser(rs));
            }

            return userOptional;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#3-2 회원조회
        String sql = "SELECT * FROM Users WHERE UserID = ? AND State != 'DELETED'";
        Connection connection = DbConnectionThreadLocal.getConnection();

        Optional<User> userOptional = Optional.empty();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) userOptional = Optional.of(createUser(rs));
            }

            return userOptional;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public Optional<User> findByUserID(String uniqueUserID) {
        //todo#3-2 회원조회
        String sql = "SELECT * FROM Users WHERE UniqueUserID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        Optional<User> userOptional = Optional.empty();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, uniqueUserID);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) userOptional = Optional.of(createUser(rs));
            }

             return userOptional;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public int save(User user) {
        //todo#3-3 회원등록, executeUpdate()을 반환합니다.
        String sql = "INSERT INTO Users VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, user.getUniqueUserID());
            psmt.setString(2, user.getUserID());
            psmt.setString(3, user.getUserName());
            psmt.setString(4, user.getUserPassword());
            psmt.setString(5, user.getUserBirth());
            psmt.setString(6, user.getUserTelephone());
            psmt.setString(7, user.getUserAuth().name());
            psmt.setTimestamp(8, Timestamp.valueOf(user.getCreatedAt()));
            psmt.setTimestamp(9, Objects.nonNull(user.getLatestLoginAt()) ? Timestamp.valueOf(user.getLatestLoginAt()) : null);
            psmt.setString(10, user.getState().name());
            psmt.setTimestamp(11, Objects.nonNull(user.getDeletedAt()) ? Timestamp.valueOf(user.getDeletedAt()) : null);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseCreateOperationException();
        }
    }

    @Override
    public int updateDelete(String uniqueID) {
        //todo#3-4 회원삭제, executeUpdate()을 반환합니다.
        String sql = "UPDATE Users " +
                "SET State = ?, DeletedAt = ? " +
                "WHERE UniqueUserID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, User.State.DELETED.name());
            psmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            psmt.setString(3, uniqueID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseDeleteOperationException();
        }
    }

    @Override
    public int updateUserInfo(User updatedUser) {
        String sql = "UPDATE Users " +
                "SET UserName = ?, UserBirth = ?, UserTelephone = ?" +
                "WHERE UniqueUserID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, updatedUser.getUserName());
            psmt.setString(2, updatedUser.getUserBirth());
            psmt.setString(3, updatedUser.getUserTelephone());
            psmt.setString(4, updatedUser.getUniqueUserID());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public int updateLoginInfo(User updatedUser) {
        String sql = "UPDATE Users " +
                "SET UserID = ?, UserPassword = ?" +
                "WHERE UniqueUserID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, updatedUser.getUserID());
            psmt.setString(2, updatedUser.getUserPassword());
            psmt.setString(3, updatedUser.getUniqueUserID());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public int updateLatestLoginAtByUserId(String uniqueID, LocalDateTime latestLoginAt) {
        //todo#3-6, 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.
        String sql = "UPDATE Users SET LatestLoginAt = ? WHERE UniqueUserID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setTimestamp(1, Timestamp.valueOf(latestLoginAt));
            psmt.setString(2, uniqueID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public int countByUserId(String uniqueUserID) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        String sql = "SELECT COUNT(*) as count FROM Users WHERE UniqueUserID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, uniqueUserID);
            psmt.executeQuery();

            int count = 0;
            try (ResultSet rs = psmt.getResultSet()) {
                if (rs.next())
                    count = rs.getInt("count");
            }

            return count;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    private int countAllUser() {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        String sql = "SELECT COUNT(*) as count FROM Users WHERE UserAuth = 'ROLE_USER'";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
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

    private int countAllAdmin() {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        String sql = "SELECT COUNT(*) as count FROM Users WHERE UserAuth = 'ROLE_ADMIN'";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
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

    public Page<User> findAllUser(int page, int pageSize) {
        int offset = (page-1) * pageSize;
        int limit = pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Users WHERE UserAuth = 'ROLE_USER' ORDER BY UserName LIMIT ?, ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);

            List<User> userList = new ArrayList<>();
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next())
                    userList.add(createUser(rs));
            }

            long total = 0;

            if(!userList.isEmpty()) {
                total = countAllUser();
            }

            return new Page<>(userList, total);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Page<User> findAllAdmin(int page, int pageSize) {
        int offset = (page-1) * pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Users WHERE UserAuth = 'ROLE_ADMIN' ORDER BY UserName LIMIT ?, ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, offset);
            psmt.setInt(2, pageSize);

            List<User> userList = new ArrayList<>();
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    userList.add(createUser(rs));
                }
            }

            long total = 0;

            if(!userList.isEmpty()) {
                total = countAllAdmin();
            }

            return new Page<>(userList, total);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findUsersByID(List<String> uniqueUserIds) {
        List<User> users = new ArrayList<>();
        if (uniqueUserIds.isEmpty())
            return users;

        StringBuilder sql = new StringBuilder("SELECT * FROM Users WHERE UniqueUserID IN (");
        for (int i = 0; i < uniqueUserIds.size(); i++) {
            sql.append("?");
            if (i < uniqueUserIds.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(")");

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < uniqueUserIds.size(); i++) {
                psmt.setString(i+1, uniqueUserIds.get(i));
            }

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next())
                    users.add(createUser(rs));
            }

            return users;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    private User createUser(ResultSet rs) throws SQLException{
        return User.builder()
                .uniqueUserID(rs.getString("UniqueUserID"))
                .userID(rs.getString("UserID"))
                .userName(rs.getString("UserName"))
                .userPassword(rs.getString("UserPassword"))
                .userBirth(rs.getString("UserBirth"))
                .userTelephone(rs.getString("UserTelephone"))
                .userAuth(User.Auth.valueOf(rs.getString("UserAuth")))
                .createdAt(Objects.nonNull(rs.getTimestamp("CreatedAt")) ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null)
                .latestLoginAt(Objects.nonNull(rs.getTimestamp("LatestLoginAt")) ? rs.getTimestamp("LatestLoginAt").toLocalDateTime() : null)
                .state(User.State.valueOf(rs.getString("State")))
                .deletedAt(Objects.nonNull(rs.getTimestamp("DeletedAt")) ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null)
                .build();
    }
}
