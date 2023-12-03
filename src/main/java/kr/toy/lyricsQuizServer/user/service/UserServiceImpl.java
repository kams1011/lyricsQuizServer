package kr.toy.lyricsQuizServer.user.service;

import kr.toy.lyricsQuizServer.config.OauthProperties;
import kr.toy.lyricsQuizServer.config.SecurityService;
import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserCreate;
import kr.toy.lyricsQuizServer.user.service.port.AuthServerAPI;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthServerAPI authServerAPI;

    private final SecurityService securityService;

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public User getByEmailAndLoginType(String email, LoginType loginType) {
        return userRepository.getByEmailAndLoginType(email, loginType);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByNickName(String nickName) {
        return userRepository.getByNickName(nickName);
    }

    @Override
    public User signUp(HttpServletResponse response, UserCreate userCreate) {
        User user = User.from(userCreate, LocalDateTime.now());
        user = userRepository.save(user);
        makeTokensAndSetCookie(user, response);

        return user;
    }

    @Override
    public User quit() {
        return null;
    }

    @Override
    public User changeNickName() {
        return null;
    }


    @Override
    public UserCreate loginHandler(HttpServletResponse response, LoginType loginType, String code){
        OauthProperties.AccessTokenResponse accessTokenResponse = authServerAPI.getAccessToken(loginType, code);
        String email = authServerAPI.getEmailBy(loginType, accessTokenResponse.getAccess_token());
        try {
            User user = getByEmailAndLoginType(email, loginType);
            makeTokensAndSetCookie(user, response);
            return null;
        } catch (NoSuchElementException e){ // 회원가입필요
            UserCreate userCreate = UserCreate.builder()
                    .email(email)
                    .loginType(loginType)
                    .build();
            return userCreate;
        }
    }


    public void makeTokensAndSetCookie(User user, HttpServletResponse response){
        String accessToken = securityService.accessTokenIssue(user.getUserSeq());
        String refreshToken = securityService.refreshTokenIssue(user.getUserSeq());
        securityService.setCookieWithToken(false, accessToken, response);
        securityService.setCookieWithToken(true, refreshToken, response);

    }

}
