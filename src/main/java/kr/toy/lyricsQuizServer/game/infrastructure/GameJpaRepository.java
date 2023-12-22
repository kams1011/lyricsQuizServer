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

    @Query("select ge from GameEntity ge where (:word is null or :word = '') or (ge.roomName like %:word%) and" +
            "(:word is null or ge.manager.nickName like %:word%)") // FIXME N+1 확인
    Page<GameEntity> findAllByRoomNameOrManager(@Param("word") String word, Pageable pageable);
}
