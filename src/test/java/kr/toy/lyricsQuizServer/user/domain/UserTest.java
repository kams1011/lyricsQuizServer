//package kr.toy.lyricsQuizServer.user.domain;
//
//import kr.toy.lyricsQuizServer.config.ConfigurationProperties.OauthProperties;
//import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
//import kr.toy.lyricsQuizServer.config.ConfigurationProperties.StorageProperties;
//import kr.toy.lyricsQuizServer.game.controller.port.GameService;
//import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
//import kr.toy.lyricsQuizServer.game.infrastructure.GameJpaRepository;
//import kr.toy.lyricsQuizServer.memory.MemoryService;
//import kr.toy.lyricsQuizServer.memory.Redis.RedisCategory;
//import kr.toy.lyricsQuizServer.memory.Redis.RedisUtil;
//import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.time.LocalDateTime;
//import java.util.*;
//
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//
//@SpringBootTest
//public class UserTest {
//
//
//
//    @Autowired
//    OauthProperties oauthProperties;
//
//    @Autowired
//    SecurityProperties securityProperties;
//
//    @Autowired
//    StorageProperties storageProperties;
//
//    @Autowired
//    RedisUtil redisUtil;
//
//    @Autowired
//    GameService gameService;
//
//
//
//
//    @Test
//    public void 스트리밍_완료된_유저를_확인할_수_있다(){
//
//        List<UserInfo> userInfoList = new ArrayList<>();
//        userInfoList.add(UserInfo.builder().userSeq(1L).build());
//        userInfoList.add(UserInfo.builder().userSeq(2L).build());
//        userInfoList.add(UserInfo.builder().userSeq(3L).build());
//        userInfoList.add(UserInfo.builder().userSeq(4L).build());
//        userInfoList.add(UserInfo.builder().userSeq(5L).build());
//        userInfoList.add(UserInfo.builder().userSeq(6L).build());
//
//
//        Set<Long> streamingCompleteList = new HashSet<>();
//        streamingCompleteList.add(1L);
//        streamingCompleteList.add(2L);
//        streamingCompleteList.add(3L);
//        streamingCompleteList.add(4L);
//        streamingCompleteList.add(5L);
//        streamingCompleteList.add(6L);
//
//        GameRoom gameRoom = GameRoom.builder().userList(userInfoList).streamingCompleteUserList(streamingCompleteList).build();
//
//        assertThat(gameRoom.isAllMemberStreamingComplete()).isFalse();
//
//    }
//
//    @Test
//    void 사용자는_로그인_할_수_있다(){
//        User user = User.builder()
//                .userSeq(1L)
//                .email("kams@hanmail.net")
//                .nickName("kams")
//                .lastLoginAt(LocalDateTime.now())
//                .isBan(false)
//                .isDeleted(false)
//                .role(Role.USER)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//
//        LocalDateTime loginTime = LocalDateTime.parse("1994-01-28T10:15:30");
//        user = user.login(loginTime); // FIXME : 로그인을 어떻게 테스트할지 생각해보자.
//
//        assertThat(user.getLastLoginAt()).isEqualTo(loginTime);
//    }
//
//    @Test
//    void 사용자는_닉네임을_변경_할_수_있다(){
//        User user = User.builder()
//                .userSeq(1L)
//                .email("kams@hanmail.net")
//                .nickName("kams")
//                .lastLoginAt(LocalDateTime.now())
//                .isBan(false)
//                .isDeleted(false)
//                .role(Role.USER)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        String nickName = "changeNickName";
//
//        LocalDateTime updateTime = LocalDateTime.now();
//        user = user.changeNickName(nickName, updateTime);
//
//        assertThat(user.getNickName()).isEqualTo(nickName);
//    }
//
//    @Test
//    void 사용자는_탈퇴_할_수_있다(){
//        User user = User.builder()
//                .userSeq(1L)
//                .email("kams@hanmail.net")
//                .nickName("kams")
//                .lastLoginAt(LocalDateTime.now())
//                .isBan(false)
//                .isDeleted(false)
//                .role(Role.USER)
//                .createdAt(LocalDateTime.now())
//                .build();
//        LocalDateTime quitTime = LocalDateTime.now();
//        user = user.quit(quitTime);
//
//        assertThat(user.getIsDeleted()).isTrue();
//    }
//
//    @Test
//    void 사용자는_다른_권한을_가질_수_있다(){
//        User user = User.builder()
//                .userSeq(1L)
//                .email("kams@hanmail.net")
//                .nickName("kams")
//                .lastLoginAt(LocalDateTime.now())
//                .isBan(false)
//                .isDeleted(false)
//                .role(Role.USER)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        Role adminRole = Role.ADMIN;
//        LocalDateTime updateTime = LocalDateTime.now();
//        user = user.grantRole(adminRole, updateTime);
//
//        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
//
//    }
//
//    @Test
//    void 사용자는_강퇴_당할_수_있다(){
//        User user = User.builder()
//                .userSeq(1L)
//                .email("kams@hanmail.net")
//                .nickName("kams")
//                .lastLoginAt(LocalDateTime.now())
//                .isBan(false)
//                .isDeleted(false)
//                .role(Role.USER)
//                .createdAt(LocalDateTime.now())
//                .build();
//        LocalDateTime banTime = LocalDateTime.now();
//        user = user.ban(banTime);
//
//        assertThat(user.getIsBan()).isTrue();
//    }
//
//
//
//}
//
//
//
//
//
