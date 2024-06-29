package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.exception.user.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.repository.user.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.service.user.UserService;
import com.nhnacademy.shoppingmall.service.user.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@WebListener
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //todo#12 application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.
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

        try {
            DbConnectionThreadLocal.initialize();
            userService.saveUser(admin);
            userService.saveUser(user);
        } catch (UserAlreadyExistsException e) {
            DbConnectionThreadLocal.setSqlError(true);
            log.error(e.getMessage());
        } finally {
            DbConnectionThreadLocal.reset();
        }
    }
}
