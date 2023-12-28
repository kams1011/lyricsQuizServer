package kr.toy.lyricsQuizServer.chat.infrastructure;

import kr.toy.lyricsQuizServer.chat.domain.ChatRoom;
import kr.toy.lyricsQuizServer.game.infrastructure.GameEntity;
import kr.toy.lyricsQuizServer.user.infrastructure.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_room_entity")
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomSeq;

    @OneToOne
    @JoinColumn(name = "game_room_seq")
    private GameEntity gameEntity; //FIXME 특정 컬럼만 먼저 가져오는 기능 있었던 것 같음.



    public ChatRoom toModel(){
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomSeq(this.chatRoomSeq)
                .build();
        return chatRoom;
    }

}
