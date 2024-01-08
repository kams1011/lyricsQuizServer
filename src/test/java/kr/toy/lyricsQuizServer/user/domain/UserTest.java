package kr.toy.lyricsQuizServer.user.domain;

import kr.toy.lyricsQuizServer.chat.domain.TopicType;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.OauthProperties;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.StorageProperties;
import kr.toy.lyricsQuizServer.config.ExcludeURL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class UserTest {


    //FIXME : 데이터를 넣어주는 반복 코드 제거하기.

    @Autowired
    OauthProperties oauthProperties;

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    StorageProperties storageProperties;

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

        LocalDateTime updateTime = LocalDateTime.now();
        user = user.changeNickName(nickName, updateTime);

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
        LocalDateTime quitTime = LocalDateTime.now();
        user = user.quit(quitTime);

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
        LocalDateTime updateTime = LocalDateTime.now();
        user = user.grantRole(adminRole, updateTime);

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
        LocalDateTime banTime = LocalDateTime.now();
        user = user.ban(banTime);

        assertThat(user.getIsBan()).isTrue();
    }

}





