package kr.toy.lyricsQuizServer.chat.controller.port;

import kr.toy.lyricsQuizServer.chat.domain.ChatRoom;
import kr.toy.lyricsQuizServer.chat.infrastructure.ChatRoomEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatService {

    void pubsub();

    List<ChatRoom> getList(Pageable pageable);

    ChatRoom create();

    ChatRoom getRoom(Long chatRoomSeq);
}
