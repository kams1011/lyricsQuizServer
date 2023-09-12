package kr.toy.lyricsQuizServer.user.infrastructure;


import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.Role;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
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

}
