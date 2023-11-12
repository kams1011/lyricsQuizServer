package kr.toy.lyricsQuizServer.user.domain;

import kr.toy.lyricsQuizServer.user.domain.dto.UserCreate;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
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

    public static User from(UserCreate userCreate, LocalDateTime createTime){
        System.out.println("$$$$");
        System.out.println(userCreate.getEmail());
        User user = User.builder()
                .email(userCreate.getEmail())
                .nickName(userCreate.getNickName())
                .lastLoginAt(createTime)
                .isBan(false)
                .isDeleted(false)
                .loginType(userCreate.getLoginType())
                .role(Role.USER)
                .createdAt(createTime)
                .updatedAt(createTime)
                .build();

        return user;
    }

    public User login(LocalDateTime loginTime){
        return User.builder()
                .userSeq(userSeq)
                .nickName(nickName)
                .email(email)
                .lastLoginAt(loginTime) // FIXME : 로그인한 시간으로 바뀌어야함.
                .isBan(isBan)
                .isDeleted(isDeleted)
                .loginType(loginType)
                .role(role)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public User grantRole(Role role, LocalDateTime updateTime){
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
                .updatedAt(updateTime) // FIXME : update한 시간으로 바뀌어야함.
                .build();
    }

    public User changeNickName(String nickName, LocalDateTime updateTime){
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
                .updatedAt(updateTime)
                .build();
    }

    public User quit(LocalDateTime quitTime){
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
                .updatedAt(quitTime)
                .build();
    }

    public User ban(LocalDateTime banTime){
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
                .updatedAt(banTime)
                .build();
    }


}
