package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameJpaRepository extends JpaRepository<GameEntity, Long> {

    Optional<GameEntity> findByTopic(String topic);

    Optional<GameEntity> findByManager(User manager);

}
