package kr.toy.lyricsQuizServer.chat.service.port;

import kr.toy.lyricsQuizServer.chat.domain.ChatRoom;

import java.awt.print.Pageable;
import java.util.List;

public interface ChatRoomRepository {

//    List<ChatRoom> findAllRoom(Pageable pageable);

    ChatRoom findRoomById(Long chatRoomSeq);

    ChatRoom createChatRoom(String name);

}
