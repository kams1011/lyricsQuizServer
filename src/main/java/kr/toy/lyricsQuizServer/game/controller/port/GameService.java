package kr.toy.lyricsQuizServer.game.controller.port;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.dto.GameCreate;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface GameService {

    List<Game> getGameList(Pageable pageable); //진행중인 상태 필요(시작 전, 진행중). 종료된 방은 안나오게.

    List<Game> getGameListByWord(String word, Pageable pageable);

    void create(GameCreate gameCreate);

    void 같이_할_사람_검색(); // TODO 현재 접속중인 인원중에 현재 게임을 진행중이지 않은 인원 ( 현재 접속여부는 다시 생각해보기 )
}
