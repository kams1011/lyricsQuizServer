package kr.toy.lyricsQuizServer.user.infrastructure;


import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.Role;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserCreate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_entity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    private String email;

    private String nickName;

    private LocalDateTime lastLoginAt;

    private Boolean isBan;

    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @Builder
    public UserEntity(Long userSeq, String email, String nickName, LocalDateTime lastLoginAt, Boolean isBan, Boolean isDeleted, LoginType loginType, Role role, LocalDateTime createdAt, LocalDateTime updatedAt){
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

    public User toModel(){
        return User.builder()
                .userSeq(userSeq)
                .email(email)
                .nickName(nickName)
                .lastLoginAt(lastLoginAt)
                .isBan(isBan)
                .isDeleted(isDeleted)
                .loginType(loginType)
                .role(role)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static UserEntity fromModel(User user){
        return UserEntity.builder()
                .userSeq(user.getUserSeq())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .lastLoginAt(user.getLastLoginAt())
                .isBan(user.getIsBan())
                .isDeleted(user.getIsDeleted())
                .loginType(user.getLoginType())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
