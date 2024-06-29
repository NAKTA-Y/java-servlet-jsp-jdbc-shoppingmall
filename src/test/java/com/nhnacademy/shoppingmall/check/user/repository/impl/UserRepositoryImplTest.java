package com.nhnacademy.shoppingmall.check.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.user.User;
import com.nhnacademy.shoppingmall.repository.user.UserRepository;
import com.nhnacademy.shoppingmall.repository.user.impl.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Optional;

//todo#3-8 Test Code가 통과하도록 UserRepositoryImpl를 구현합니다.
@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class UserRepositoryImplTest {
    UserRepository userRepository = new UserRepositoryImpl();

    User testUser;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testUser = User.builder()
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

        userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("로그인: user 조회 by userId and userPassword")
    void findByUserIdAndUserPassword() {
        Optional<User> userOptional = userRepository.findByUserIdAndUserPassword(testUser.getUserID(),testUser.getUserPassword());
        Assertions.assertEquals(testUser, userOptional.orElse(null));
    }

    @Test
    @Order(2)
    @DisplayName("로그인 : sql injection 방어")
    void findByUserIdAndUserPassword_sql_injection(){
        //테스트 코드가 통과할 수 있도록  userRepository.findByUserIdAndUserPassword를 수정하세요.
        String password="' or '1'='1";
        Optional<User> userOptional = userRepository.findByUserIdAndUserPassword(testUser.getUserID(),password);
        log.debug("user:{}",userOptional.orElse(null));
        Assertions.assertFalse(userOptional.isPresent());
    }

    @Test
    @Order(3)
    @DisplayName("user 조회 by userId")
    void findByUserID() {
        Optional<User> userOptional = userRepository.findByUserID(testUser.getUniqueUserID());
        Assertions.assertEquals(testUser,userOptional.get());
    }

    @Test
    @Order(4)
    @DisplayName("user 등록")
    void save() {
        User newUser = User.builder()
                .uniqueUserID("nhnacademy-unique-id2")
                .userID("nhnacademy-test-user2")
                .userName("nhn아카데미2")
                .userPassword("nhnacademy-test-password2")
                .userBirth("19990101")
                .userTelephone("010-1234-1234")
                .userAuth(User.Auth.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .state(User.State.ACTIVE)
                .build();

        int result = userRepository.save(newUser);
        Assertions.assertAll(
                ()->Assertions.assertEquals(1,result),
                ()->Assertions.assertEquals(newUser, userRepository.findByUserID(newUser.getUniqueUserID()).get())
        );
    }

    @Test
    @Order(5)
    @DisplayName("user 중복 등록 - 제약조건 확인")
    void save_duplicate_user_id() {

        Throwable throwable = Assertions.assertThrows(RuntimeException.class,()->{
            userRepository.save(testUser);
        });
        Assertions.assertTrue(throwable.getMessage().contains(SQLIntegrityConstraintViolationException.class.getName()));
        log.debug("errorMessage:{}", throwable.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("user 삭제")
    void deleteByUserId() {
        int result = userRepository.updateDelete(testUser.getUniqueUserID());

        User user = userRepository.findByUserID(testUser.getUniqueUserID()).orElse(null);
        Assertions.assertAll(
                ()->Assertions.assertEquals(1,result),
                () -> Assertions.assertEquals(User.State.DELETED, user.getState())
        );
    }

    @Test
    @Order(7)
    @DisplayName("user info 수정")
    void updateUserInfo() {
        String updateUserName = "updateUserName";
        String updateUserBirth = "20001010";

        testUser.updateUserInfo(updateUserName, updateUserBirth, testUser.getUserTelephone());

        int result = userRepository.updateUserInfo(testUser);
        User findUser = userRepository.findByUserID(testUser.getUniqueUserID()).get();
        Assertions.assertAll(
                ()-> Assertions.assertEquals(1,result),
                ()-> Assertions.assertEquals(updateUserName, findUser.getUserName()),
                ()-> Assertions.assertEquals(updateUserBirth, findUser.getUserBirth())
        );
    }

    @Test
    @Order(8)
    @DisplayName("user login info 수정")
    void updateLoginInfo() {
        String updateUserID = "updatedUserID";
        String updateUserPassword = "updatedUserPassword";

        testUser.updateLoginInfo(updateUserID, updateUserPassword);

        int result = userRepository.updateLoginInfo(testUser);
        User findUser = userRepository.findByUserID(testUser.getUniqueUserID()).get();
        Assertions.assertAll(
                ()-> Assertions.assertEquals(1,result),
                ()-> Assertions.assertEquals(updateUserID, findUser.getUserID()),
                ()-> Assertions.assertEquals(updateUserPassword, findUser.getUserPassword())
        );
    }

    @Test
    @Order(9)
    @DisplayName("user count")
    void countByUserId() {
        int count = userRepository.countByUserId(testUser.getUniqueUserID());
        Assertions.assertEquals(1,count);
    }

    @Test
    @Order(10)
    @DisplayName("user 삭제")
    void deleteUser() {
        int result = userRepository.updateDelete(testUser.getUniqueUserID());

        int count = userRepository.countByUserId(testUser.getUniqueUserID());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(1, count)
        );
    }

    @Test
    @Order(11)
    @DisplayName("최근 로그인시간 update")
    void updateLatestLoginAtByUserId() {
        int result = userRepository.updateLatestLoginAtByUserId(testUser.getUniqueUserID(), LocalDateTime.now());
        Assertions.assertEquals(1,result);
    }

    @Test
    @Order(12)
    @DisplayName("user_pagination")
    void findAllUser() {
        Page<User> userPage = userRepository.findAllUser(1, 10);
        Assertions.assertEquals(2, userPage.getContent().size());
    }

    @Test
    @Order(13)
    @DisplayName("admin_pagination")
    void findAllAdmin() {
        Page<User> userPage = userRepository.findAllAdmin(1, 10);
        Assertions.assertEquals(1, userPage.getContent().size());
    }

    @Test
    @Order(14)
    @DisplayName("find by id")
    void findByID() {
        Optional<User> user = userRepository.findById(testUser.getUserID());
        Assertions.assertAll(
                () -> Assertions.assertTrue(user.isPresent()),
                () -> Assertions.assertEquals(testUser, user.get())
        );
    }
}