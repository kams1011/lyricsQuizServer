package kr.toy.lyricsQuizServer.game.service.port;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.user.domain.User;

import java.util.List;

public interface GameRepository {

    List<Game> findAll();

    Game findByTopic(String topic);

    Game findByManager(User manager);

}
