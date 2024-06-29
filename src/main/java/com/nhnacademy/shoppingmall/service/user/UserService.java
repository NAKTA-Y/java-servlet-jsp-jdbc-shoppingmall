package com.nhnacademy.shoppingmall.service.user;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.user.User;

import java.util.List;

public interface UserService {

    User getUser(String userId);
    void saveUser(User user);
    void updateUserInfo(User user);
    void updateLoginInfo(User user);
    void deleteUser(String userId);
    User doLogin(String userId, String userPassword);
    boolean isExistUserByID(String userId);
    Page<User> getUserPage(int page, int pageSize);
    Page<User> getAdminPage(int page, int pageSize);
    List<User> getUsersByID(List<String> uniqueUserIds);
}