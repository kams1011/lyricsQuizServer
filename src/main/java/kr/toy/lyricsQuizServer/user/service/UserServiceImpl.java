package kr.toy.lyricsQuizServer.user.service;

import kr.toy.lyricsQuizServer.user.controller.port.UserService;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserCreate;
import kr.toy.lyricsQuizServer.user.infrastructure.AuthServerAPIImpl;
import kr.toy.lyricsQuizServer.user.service.port.AuthServerAPI;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthServerAPI authServerAPI;

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
    public User signUp(UserCreate userCreate) {
        User user = User.from(userCreate, LocalDateTime.now());
        user = userRepository.save(user);
        //AccessToken을 받는다.
        //LoginType을 받는다.
        //AccessToken으로 email을 받는다.
        //RefreshToken을 생성한다.
        //UserEntity를 생성한다.
        //저장한다.

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
}
