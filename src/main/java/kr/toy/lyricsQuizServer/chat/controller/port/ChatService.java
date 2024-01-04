package kr.toy.lyricsQuizServer.chat.controller.port;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.user.domain.User;

public interface ChatService {

    GameRoom create(GameRoom gameRoom);

    void sendMessage(ChatMessage message, User user);

}
