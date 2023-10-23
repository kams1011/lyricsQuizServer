package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameJpaRepository extends JpaRepository<GameEntity, Long> {

    List<GameEntity> findAllByRoomNameOrManager(String roomName, User manager);
}
