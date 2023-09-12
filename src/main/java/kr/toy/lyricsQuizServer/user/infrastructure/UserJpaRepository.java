package kr.toy.lyricsQuizServer.user.infrastructure;

import kr.toy.lyricsQuizServer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
