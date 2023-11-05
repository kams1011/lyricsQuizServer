package kr.toy.lyricsQuizServer.user.service;

import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
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
    public User signUp(LoginType loginType, String accessToken) {
        //AccessToken을 받는다.
        //LoginType을 받는다.
        //AccessToken으로 email을 받는다.
        //RefreshToken을 생성한다.
        //UserEntity를 생성한다.
        //저장한다.

        return null;
    }

    @Override
    public User quit() {
        return null;
    }

    @Override
    public User changeNickName() {
        return null;
    }
}
