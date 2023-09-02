package kr.toy.lyricsQuizServer.user.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
public class User {

    Long userSeq;

    String email;

    String nickName;

    LocalDateTime lastLoginAt;

    Boolean isBan;

    Boolean isDeleted;

    LoginType loginType;

    Role role;

    LocalDateTime createdAt;

    LocalDateTime updatedAt; // JPAAuditing 사용하면 어떻게 변경할지 생각


    @Builder
    public User(Long userSeq, String email, String nickName, LocalDateTime lastLoginAt, Boolean isBan, Boolean isDeleted, LoginType loginType, Role role, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.userSeq = userSeq;
        this.email = email;
        this.nickName = nickName;
        this.lastLoginAt = lastLoginAt;
        this.isBan = isBan;
        this.isDeleted = isDeleted;
        this.loginType = loginType;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User login(){
        return User.builder()
                .userSeq(userSeq)
                .nickName(nickName)
                .email(email)
                .lastLoginAt(lastLoginAt) // FIXME : 로그인한 시간으로 바뀌어야함.
                .isBan(isBan)
                .isDeleted(isDeleted)
                .loginType(loginType)
                .role(role)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public User grantRole(Role role){
        return User.builder()
                .userSeq(userSeq)
                .nickName(nickName)
                .email(email)
                .lastLoginAt(lastLoginAt)
                .isBan(isBan)
                .isDeleted(isDeleted)
                .loginType(loginType)
                .role(role)
                .createdAt(createdAt)
                .updatedAt(LocalDateTime.now()) // FIXME : update한 시간으로 바뀌어야함.
                .build();
    }

    public User changeNickName(String nickName){
        Instant.now();
        return User.builder()
                .userSeq(userSeq)
                .nickName(nickName)
                .email(email)
                .lastLoginAt(lastLoginAt)
                .isBan(isBan)
                .isDeleted(isDeleted)
                .loginType(loginType)
                .role(role)
                .createdAt(createdAt)
                .updatedAt(updatedAt) // FIXME : update한 시간으로 바뀌어야함.
                .build();
    }

    public User quit(){
        return User.builder()
                .userSeq(userSeq)
                .nickName(nickName)
                .email(email)
                .lastLoginAt(lastLoginAt)
                .isBan(isBan)
                .isDeleted(true)
                .loginType(loginType)
                .role(role)
                .createdAt(createdAt)
                .updatedAt(updatedAt) // FIXME : update한 시간으로 바뀌어야함.
                .build();
    }

    public User ban(){
        return User.builder()
                .userSeq(userSeq)
                .nickName(nickName)
                .email(email)
                .lastLoginAt(lastLoginAt)
                .isBan(true)
                .isDeleted(isDeleted)
                .loginType(loginType)
                .role(role)
                .createdAt(createdAt)
                .updatedAt(updatedAt) // FIXME : update한 시간으로 바뀌어야함.
                .build();
    }


}
