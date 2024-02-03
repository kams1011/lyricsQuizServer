package kr.toy.lyricsQuizServer.chat.controller.port;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;

public interface ChatService {

    void sendMessage(ChatMessage message, UserInfo user);

    GameRoom createGameRoom(GameRoom gameRoom);

    GameRoom getGameRoom(Long gameRoomSeq);

    UserInfo findUserInfoOrCreate(User user, Long gameRoomSeq);
}
