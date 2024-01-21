package kr.toy.lyricsQuizServer.chat.controller.port;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.user.domain.User;

public interface ChatService {

    void sendMessage(ChatMessage message);

    GameRoom createGameRoom(GameRoom gameRoom);
}
