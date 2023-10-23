package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface GameJpaRepository extends JpaRepository<GameEntity, Long> {

    Page<GameEntity> findAll(Pageable pageable);

    Page<GameEntity> findAllByRoomNameOrManager(String roomName, User manager, Pageable pageable);
}
