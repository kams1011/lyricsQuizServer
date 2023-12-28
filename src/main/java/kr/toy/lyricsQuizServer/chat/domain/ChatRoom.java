package kr.toy.lyricsQuizServer.chat.domain;

import kr.toy.lyricsQuizServer.chat.infrastructure.ChatRoomEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class ChatRoom {

    private Long chatRoomSeq;

    private String name; // FIXME 이름도 Game에서 가져오는 걸로 변경할듯?

    @Builder
    public ChatRoom(Long chatRoomSeq, String name){
        this.chatRoomSeq = chatRoomSeq;
        this.name = name;
    }

}