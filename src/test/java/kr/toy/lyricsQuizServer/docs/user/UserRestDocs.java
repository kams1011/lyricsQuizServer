package kr.toy.lyricsQuizServer.docs.user;

import kr.toy.lyricsQuizServer.docs.RestDocsSupport;
import kr.toy.lyricsQuizServer.user.controller.UserController;
import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.Role;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;

public class UserRestDocs extends RestDocsSupport<User> {

    @MockBean
    protected UserService userService;

    @MockBean
    protected UserRepository userRepository;

    protected final String apiUrl = "/api/users";

    @Autowired
    protected UserController userController;

    @Override
    protected Object getController() {
        return this.userController;
    }
    @Override
    protected PageImpl<User> getPageImpl() {
        return null;
    }

    @Override
    protected User initializeDummyData() {
        User user = User.builder()
                .userSeq(getId())
                .email("kams1011@naver.com")
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(false)
                .isBan(false)
                .nickName("kams")
                .loginType(LoginType.NAVER)
                .lastLoginAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        return user;
    }

}
