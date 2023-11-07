package kr.toy.lyricsQuizServer.user.infrastructure;

import kr.toy.lyricsQuizServer.user.domain.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndLoginType(String email, LoginType loginType);

    Optional<UserEntity> findByNickName(String nickName);
}
