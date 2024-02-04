package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameJpaRepository extends JpaRepository<GameEntity, Long> {

    Page<GameEntity> findAll(Pageable pageable);

    @Query("SELECT ge FROM GameEntity ge " +
            "WHERE (:word IS NULL OR :word = '' " +
            "       OR ge.roomName LIKE %:word% " +
            "       OR ge.host.nickName LIKE %:word%) " +
            "    AND ge.gameStatus <> 'FINISHED' AND ge.isDeleted = false") // FIXME N+1 확인
    Page<GameEntity> findAllByRoomNameOrHost(@Param("word") String word, Pageable pageable);
}
