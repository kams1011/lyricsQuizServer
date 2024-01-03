package kr.toy.lyricsQuizServer.chat.controller.port;

import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;

public interface ChatService {

    GameRoom create(GameRoom gameRoom);

}
