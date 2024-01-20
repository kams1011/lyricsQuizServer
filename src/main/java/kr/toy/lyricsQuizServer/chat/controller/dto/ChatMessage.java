package kr.toy.lyricsQuizServer.chat.controller.dto;


import kr.toy.lyricsQuizServer.chat.domain.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessage {
    // 메시지 타입 : 입장, 채팅

    private MessageType type; // 메시지 타입

    private String roomId; // 방번호

    private String senderNickName; // 메시지 보낸사람

    private String message; // 메시지

    public void setMessage(String message){
        this.message = message;
    }


    @Builder
    public ChatMessage(MessageType type, String roomId, String senderNickName, String message){
        this.type = type;
        this.roomId = roomId;
        this.senderNickName = senderNickName;
        this.message = message;
    }

    public void setSender(String senderNickName){
        this.senderNickName = senderNickName;
    }

    public ChatMessage join(String participant){
        ChatMessage chatMessage = ChatMessage.builder()
                .type(MessageType.ENTER)
                .senderNickName("[알림]")
                .message(participant + "님이 입장하셨습니다.")
                .build();
        return chatMessage;
    }
}
