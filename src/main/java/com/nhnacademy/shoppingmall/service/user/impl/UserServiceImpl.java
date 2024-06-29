package com.nhnacademy.shoppingmall.service.user.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.exception.user.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.exception.user.UserNotFoundException;
import com.nhnacademy.shoppingmall.repository.user.UserRepository;
import com.nhnacademy.shoppingmall.service.user.UserService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "유저를 찾지 못했습니다.";
    private static final String USER_ALREADY_EXISTS_EXCEPTION_MESSAGE = "이미 존재하는 유저입니다.";

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String uniqueID){
        //todo#4-1 회원조회
        Optional<User> userOptional = userRepository.findById(uniqueID);
        if (userOptional.isEmpty()) throw new UserNotFoundException(HttpServletResponse.SC_NOT_FOUND, USER_NOT_FOUND_EXCEPTION_MESSAGE);
        return userOptional.get();
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록
        Optional<User> userOptional = userRepository.findById(user.getUserID());
        if (!userOptional.isEmpty())
            throw new UserAlreadyExistsException(HttpServletResponse.SC_CONFLICT, USER_ALREADY_EXISTS_EXCEPTION_MESSAGE);

        userRepository.save(user);
    }

    @Override
    public void updateUserInfo(User user) {
        if (userRepository.countByUserId(user.getUniqueUserID()) > 0)
            userRepository.updateUserInfo(user);
    }

    @Override
    public void updateLoginInfo(User user) {
        if (userRepository.countByUserId(user.getUniqueUserID()) > 0)
            userRepository.updateLoginInfo(user);
    }

    @Override
    public void deleteUser(String userId) {
        //todo#4-4 회원삭제
        if (userRepository.countByUserId(userId) > 0) {
            userRepository.updateDelete(userId);
        }
    }

    @Override
    public User doLogin(String userId, String userPassword) {
        //todo#4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회
        Optional<User> userOptional = userRepository.findByUserIdAndUserPassword(userId, userPassword);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(HttpServletResponse.SC_UNAUTHORIZED, USER_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        User user = userOptional.get();
        userRepository.updateLatestLoginAtByUserId(user.getUniqueUserID(), LocalDateTime.now());
        return user;
    }

    @Override
    public boolean isExistUserByID(String userId) {
        return userRepository.countByUserId(userId) > 0;
    }

    @Override
    public Page<User> getUserPage(int page, int pageSize) {
        return userRepository.findAllUser(page, pageSize);
    }

    @Override
    public Page<User> getAdminPage(int page, int pageSize) {
        return userRepository.findAllAdmin(page, pageSize);
    }

    @Override
    public List<User> getUsersByID(List<String> uniqueUserIds) {
        return userRepository.findUsersByID(uniqueUserIds);
    }
}