package com.nhnacademy.shoppingmall.check.user.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.exception.user.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.exception.user.UserNotFoundException;
import com.nhnacademy.shoppingmall.repository.user.UserRepository;
import com.nhnacademy.shoppingmall.service.user.UserService;
import com.nhnacademy.shoppingmall.service.user.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;

//todo#4-6 Test Code가 통과하도록 UserServiceImpl을 구현합니다.

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(userRepository);
    User testUser = User.builder()
                .uniqueUserID("nhnacademy-unique-id")
                .userID("nhnacademy-test-user")
                .userName("nhn아카데미")
                .userPassword("nhnacademy-test-password")
                .userBirth("19990101")
                .userTelephone("010-1234-1234")
                .userAuth(User.Auth.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .state(User.State.ACTIVE)
                .build();
    User admin = User.builder()
            .uniqueUserID(UUID.randomUUID().toString())
            .userID("admin")
            .userName("관리자")
            .userPassword("12345")
            .userBirth("19990101")
            .userTelephone("010-1234-1234")
            .userAuth(User.Auth.ROLE_ADMIN)
            .createdAt(LocalDateTime.now())
            .state(User.State.ACTIVE)
            .build();

    User user = User.builder()
            .uniqueUserID(UUID.randomUUID().toString())
            .userID("user")
            .userName("테스트 유저")
            .userPassword("12345")
            .userBirth("20001010")
            .userTelephone("010-1234-1234")
            .userAuth(User.Auth.ROLE_USER)
            .createdAt(LocalDateTime.now())
            .state(User.State.ACTIVE)
            .build();

    @Test
    @DisplayName("getUser")
    void getUser() {
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(testUser));
        userService.getUser(testUser.getUniqueUserID());
        Mockito.verify(userRepository,Mockito.times(1)).findById(anyString());
    }

    @Test
    @DisplayName("getUser - user not found -> return null")
    void getUser_not_found(){
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        User user = userService.getUser(testUser.getUserID());
        Assertions.assertNull(user);
    }

    @Test
    @DisplayName("save user")
    void saveUser() {

        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(0);
        Mockito.when(userRepository.save(any())).thenReturn(1);
        userService.saveUser(testUser);
        Mockito.verify(userRepository,Mockito.times(1)).countByUserId(anyString());
        Mockito.verify(userRepository,Mockito.times(1)).save(any());

    }

    @Test
    @DisplayName("save user -aready exist user")
    void saveUser_exist(){
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(1);
        Throwable throwable = Assertions.assertThrows(UserAlreadyExistsException.class,()->userService.saveUser(testUser));
        log.debug("error:{}",throwable.getMessage());
    }

    @Test
    @DisplayName("update user info")
    void updateUserInfo() {
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(1);
        Mockito.when(userRepository.updateUserInfo(testUser)).thenReturn(1);
        userService.updateUserInfo(testUser);
        Mockito.verify(userRepository,Mockito.times(1)).updateUserInfo(any());
        Mockito.verify(userRepository,Mockito.times(1)).countByUserId(anyString());
    }

    @Test
    @DisplayName("update user login info")
    void updateLoginInfo() {
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(1);
        Mockito.when(userRepository.updateLoginInfo(testUser)).thenReturn(1);
        userService.updateLoginInfo(testUser);
        Mockito.verify(userRepository,Mockito.times(1)).updateLoginInfo(any());
        Mockito.verify(userRepository,Mockito.times(1)).countByUserId(anyString());
    }

    @Test
    @DisplayName("delete user")
    void deleteUser() {
        Mockito.when(userRepository.updateDelete(anyString())).thenReturn(1);
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(1);

        userService.deleteUser(testUser.getUniqueUserID());

        Mockito.verify(userRepository,Mockito.times(1)).updateDelete(anyString());
        Mockito.verify(userRepository,Mockito.times(1)).countByUserId(anyString());

    }

    @Test
    @DisplayName("login - success")
    void doLogin() {
        Mockito.when(userRepository.findByUserIdAndUserPassword(anyString(),anyString())).thenReturn(Optional.ofNullable(testUser));
        Mockito.when(userRepository.updateLatestLoginAtByUserId(anyString(),any())).thenReturn(1);

        User user = userService.doLogin(testUser.getUserID(), testUser.getUserPassword());

        Assertions.assertEquals(testUser,user);
        Mockito.verify(userRepository,Mockito.times(1)).findByUserIdAndUserPassword(anyString(),anyString());
        Mockito.verify(userRepository,Mockito.times(1)).updateLatestLoginAtByUserId(anyString(),any());
    }

    @Test
    @DisplayName("login fail")
    void doLogin_fail(){
        Mockito.when(userRepository.findByUserIdAndUserPassword(anyString(),anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,()->userService.doLogin(testUser.getUserID(), testUser.getUserPassword()));
        Mockito.verify(userRepository,Mockito.times(1)).findByUserIdAndUserPassword(anyString(),anyString());
    }

    @Test
    @DisplayName("user_pagination")
    void user_pagination() {
        List<User> userList = new ArrayList<>();
        userList.add(testUser);
        userList.add(user);

        Mockito.when(userRepository.findAllUser(anyInt(), anyInt())).thenReturn(new Page<>(userList, 2));

        Page<User> userPage = userRepository.findAllUser(1, 10);

        Assertions.assertEquals(userList, userPage.getContent());
        Mockito.verify(userRepository, Mockito.times(1)).findAllUser(anyInt(), anyInt());
    }

    @Test
    @DisplayName("admin_pagination")
    void admin_pagination() {
        List<User> userList = new ArrayList<>();
        userList.add(admin);

        Mockito.when(userRepository.findAllAdmin(anyInt(), anyInt())).thenReturn(new Page<>(userList, 1));

        Page<User> userPage = userRepository.findAllAdmin(1, 10);

        Assertions.assertEquals(userList, userPage.getContent());
        Mockito.verify(userRepository, Mockito.times(1)).findAllAdmin(anyInt(), anyInt());
    }
}