package kr.toy.lyricsQuizServer.user.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UserTest {

    @Test
    void 사용자는_로그인_할_수_있다(){
        User user = User.builder()
                .userSeq(1L)
                .email("kams@hanmail.net")
                .nickName("kams")
                .lastLoginAt(LocalDateTime.now())
                .isBan(false)
                .isDeleted(false)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();


        LocalDateTime loginTime = LocalDateTime.parse("1994-01-28T10:15:30");
        user = user.login(loginTime); // FIXME : 로그인을 어떻게 테스트할지 생각해보자.

        assertThat(user.getLastLoginAt()).isEqualTo(loginTime);
    }

    @Test
    void 사용자는_닉네임을_변경_할_수_있다(){
        User user = User.builder()
                .userSeq(1L)
                .email("kams@hanmail.net")
                .nickName("kams")
                .lastLoginAt(LocalDateTime.now())
                .isBan(false)
                .isDeleted(false)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        String nickName = "changeNickName";

        user = user.changeNickName(nickName);

        assertThat(user.getNickName()).isEqualTo(nickName);
    }

    @Test
    void 사용자는_탈퇴_할_수_있다(){
        User user = User.builder()
                .userSeq(1L)
                .email("kams@hanmail.net")
                .nickName("kams")
                .lastLoginAt(LocalDateTime.now())
                .isBan(false)
                .isDeleted(false)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        user = user.quit();

        assertThat(user.getIsDeleted()).isTrue();
    }

    @Test
    void 사용자는_다른_권한을_가질_수_있다(){
        User user = User.builder()
                .userSeq(1L)
                .email("kams@hanmail.net")
                .nickName("kams")
                .lastLoginAt(LocalDateTime.now())
                .isBan(false)
                .isDeleted(false)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        Role adminRole = Role.ADMIN;

        user = user.grantRole(adminRole);

        assertThat(user.getRole()).isEqualTo(Role.ADMIN);

    }

    @Test
    void 사용자는_강퇴_당할_수_있다(){
        User user = User.builder()
                .userSeq(1L)
                .email("kams@hanmail.net")
                .nickName("kams")
                .lastLoginAt(LocalDateTime.now())
                .isBan(false)
                .isDeleted(false)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        user = user.ban();

        assertThat(user.getIsBan()).isTrue();
    }


}
