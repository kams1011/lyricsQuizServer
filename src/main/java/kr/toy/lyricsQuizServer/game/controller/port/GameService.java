package kr.toy.lyricsQuizServer.game.controller.port;

import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.infrastructure.GameEntity;
import kr.toy.lyricsQuizServer.user.domain.User;

import java.util.List;

public interface GameService {

    List<Game> 로비_게임리스트_조회();

    List<GameEntity> findAllByRoomNameOrManager(String roomName, User manager);

    void 방_생성();

    void 같이_할_사람_검색(); // TODO 현재 접속중인 인원중에 현재 게임을 진행중이지 않은 인원 ( 현재 접속여부는 다시 생각해보기 )
}
