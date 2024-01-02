package kr.toy.lyricsQuizServer.chat.domain;

import kr.toy.lyricsQuizServer.game.domain.Game;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ChatRoom implements Serializable {

    private Long chatRoomSeq;

    private Game game; // FIXME 이름도 Game에서 가져오는 걸로 변경할듯?

    @Builder
    public ChatRoom(Long chatRoomSeq, Game game){
        this.chatRoomSeq = chatRoomSeq;
        this.game = game;
    }

}