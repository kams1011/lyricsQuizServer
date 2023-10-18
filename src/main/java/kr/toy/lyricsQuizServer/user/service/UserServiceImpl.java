package kr.toy.lyricsQuizServer.user.service;

import kr.toy.lyricsQuizServer.user.controller.port.UserService;
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
    public User signUp() {
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
