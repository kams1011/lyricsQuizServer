package kr.toy.lyricsQuizServer.game.service.port;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.GameStatus;
import kr.toy.lyricsQuizServer.game.domain.dto.GameCreate;
import kr.toy.lyricsQuizServer.game.infrastructure.GameEntity;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.user.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository {

    Game findById(Long gameSeq);

    PageImpl<Game> findAll(Pageable pageable);

    PageImpl<Game> findAllByRoomNameOrManagerName(String word, Pageable pageable);

    Game save(User host, Game game, Quiz quiz);

}
