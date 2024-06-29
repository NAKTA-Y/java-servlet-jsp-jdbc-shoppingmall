package com.nhnacademy.shoppingmall.entity.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
public class User {
    public enum Auth {
        ROLE_ADMIN, ROLE_USER
    }

    public enum State {
        ACTIVE, DELETED
    }

    private final   String          uniqueUserID;
    private         String          userID;
    private         String          userName;
    private         String          userPassword;
    private         String          userBirth;
    private         String          userTelephone;
    private final   Auth            userAuth;
    private final   LocalDateTime   createdAt;
    private         LocalDateTime   latestLoginAt;
    private         State           state;
    private         LocalDateTime   deletedAt;

    public void updateLoginInfo(String userId, String userPassword) {
        this.userID = userId;
        this.userPassword   = userPassword;
    }

    public void updateUserInfo(String userName, String userBirth, String userTelephone) {
        this.userName       = userName;
        this.userBirth      = userBirth;
        this.userTelephone  = userTelephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return     Objects.equals(uniqueUserID, user.uniqueUserID)
                && Objects.equals(userID, user.userID)
                && Objects.equals(userName, user.userName)
                && Objects.equals(userPassword, user.userPassword)
                && Objects.equals(userBirth, user.userBirth)
                && Objects.equals(userTelephone, user.userTelephone)
                && Objects.equals(userAuth, user.userAuth)
                && Objects.equals(createdAt, user.createdAt)
                && Objects.equals(state, user.state)
                && Objects.equals(deletedAt, user.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueUserID, userID, userName, userPassword, userBirth, userTelephone, userAuth, createdAt, state, deletedAt);
    }

    @Override
    public String toString() {
        return "User{" +
                "uniqueUserID='" + uniqueUserID + '\'' +
                ", userId='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userBirth='" + userBirth + '\'' +
                ", userTelephone='" + userTelephone + '\'' +
                ", userAuth=" + userAuth +
                ", createdAt=" + createdAt +
                ", latestLoginAt=" + latestLoginAt +
                ", state=" + state +
                ", deletedAt=" + deletedAt +
                '}';
    }
}