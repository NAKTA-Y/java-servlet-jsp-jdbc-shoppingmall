package com.nhnacademy.shoppingmall.repository.user.impl;

import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseCreateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseDeleteOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseReadOperationException;
import com.nhnacademy.shoppingmall.common.mvc.exception.database.DatabaseUpdateOperationException;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.entity.product.Product;
import com.nhnacademy.shoppingmall.entity.user.Address;
import com.nhnacademy.shoppingmall.repository.user.AddressRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class AddressRepositoryImpl implements AddressRepository {
    @Override
    public int saveAddress(Address address) {
        String sql = "INSERT INTO Addresses VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)";

        int pk = 0;
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            psmt.setString(1, address.getAddress());
            psmt.setString(2, address.getAddressDetail());
            psmt.setString(3, address.getZipcode());
            psmt.setString(4, address.getName());
            psmt.setString(5, address.getTelephone());
            psmt.setString(6, address.getRequest());
            psmt.setBoolean(7, address.isDefaultAddress());
            psmt.setString(8, address.getUniqueUserID());

            psmt.executeUpdate();
            try (ResultSet rs = psmt.getGeneratedKeys()) {
                if (rs.next()) pk = rs.getInt(1);
            }

            return pk;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseCreateOperationException();
        }
    }

    @Override
    public Optional<Address> getAddressByID(int addressID) {
        String sql = "SELECT * FROM Addresses WHERE AddressID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        Optional<Address> optionalAddress = Optional.empty();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, addressID);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next())
                    optionalAddress = Optional.of(createAddress(rs));
            }

            return optionalAddress;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public int updateAddressInfo(Address address) {
        String sql = "UPDATE Addresses " +
                "SET Address = ?, AddressDetail = ?, zipcode = ?, Name = ?, Telephone = ?, Request = ? " +
                "WHERE AddressID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, address.getAddress());
            psmt.setString(2, address.getAddressDetail());
            psmt.setString(3, address.getZipcode());
            psmt.setString(4, address.getName());
            psmt.setString(5, address.getTelephone());
            psmt.setString(6, address.getRequest());
            psmt.setInt(7, address.getAddressID());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public int setDefaultAddress(int addressID, boolean isDefault) {
        String sql = "UPDATE Addresses SET isDefault = ? WHERE AddressID = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setBoolean(1, isDefault);
            psmt.setInt(2, addressID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseUpdateOperationException();
        }
    }

    @Override
    public int deleteAddressByID(int addressID) {
        String sql = "DELETE FROM Addresses WHERE AddressID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, addressID);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseDeleteOperationException();
        }
    }

    @Override
    public int countDefaultAddress(String uniqueUserID) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        String sql = "SELECT COUNT(*) as count FROM Addresses WHERE isDefault = true";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            int count = 0;
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) count = rs.getInt("count");
            }

            return count;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public int countAddressByUserID(String uniqueUserID) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        String sql = "SELECT COUNT(*) as count FROM Addresses WHERE UniqueUserID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, uniqueUserID);

            int count = 0;
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) count = rs.getInt("count");
            }

             return count;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    @Override
    public List<Address> getAddressesByUserID(String uniqueUserID) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        String sql = "SELECT * FROM Addresses WHERE UniqueUserID = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, uniqueUserID);

            List<Address> addresses = new ArrayList<>();
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) addresses.add(createAddress(rs));
            }

            return addresses;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseReadOperationException();
        }
    }

    private Address createAddress(ResultSet rs) throws SQLException {
        return Address.builder()
                .addressID(rs.getInt("AddressID"))
                .address(rs.getString("Address"))
                .addressDetail(rs.getString("AddressDetail"))
                .zipcode(rs.getString("zipcode"))
                .name(rs.getString("Name"))
                .telephone(rs.getString("Telephone"))
                .request(rs.getString("Request"))
                .defaultAddress(rs.getBoolean("isDefault"))
                .uniqueUserID(rs.getString("UniqueUserID"))
                .build();
    }
}
