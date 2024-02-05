package kr.toy.lyricsQuizServer.user.infrastructure;


import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User getById(Long id) {
        return userJpaRepository.findByUserSeqAndIsDeletedIsFalseAndIsBanIsFalse(id).orElseThrow(NoSuchElementException::new).toModel();
    }

    @Override
    public User getByEmail(String email) {
        return userJpaRepository.findByEmail(email).orElseThrow(NoSuchElementException::new).toModel();
    }

    @Override
    public User getByEmailAndLoginType(String email, LoginType loginType) {
        return userJpaRepository.findByEmailAndLoginType(email, loginType).orElseThrow(NoSuchElementException::new).toModel();
    }

    @Override
    public User getByNickName(String nickName) {
        return userJpaRepository.findByNickName(nickName).orElseThrow(NoSuchElementException::new).toModel();
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.fromModel(user)).toModel();
    }
}
