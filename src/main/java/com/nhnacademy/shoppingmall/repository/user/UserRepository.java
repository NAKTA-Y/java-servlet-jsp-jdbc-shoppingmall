package com.nhnacademy.shoppingmall.repository.user;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    int save(User user);
    Optional<User> findById(String id);
    Optional<User> findByUserID(String uniqueUserID);
    // 개인 정보만 수정하는 경우
    // 로그인 정보 수정하는 경우
    int updateUserInfo(User updatedUser);
    int updateLoginInfo(User updatedUser);
    int updateDelete(String id);
    Optional<User> findByUserIdAndUserPassword(String userId, String userPassword);
    int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt);
    int countByUserId(String userId);
    Page<User> findAllUser(int page, int pageSize);
    Page<User> findAllAdmin(int page, int pageSize);
    List<User> findUsersByID(List<String> uniqueUserID);
}
