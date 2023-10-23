package kr.toy.lyricsQuizServer.game.service.port;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.infrastructure.GameEntity;
import kr.toy.lyricsQuizServer.user.domain.User;

import java.util.List;

public interface GameRepository {

    List<Game> findAll();

    List<GameEntity> findAllByRoomNameOrManager(String word);


}
