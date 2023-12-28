package kr.toy.lyricsQuizServer.chat.controller.dto;


import com.amazonaws.services.kms.model.MessageType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatMessage {
    // 메시지 타입 : 입장, 채팅

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지

    public void setMessage(String message){
        this.message = message;
    }


    @Builder
    public ChatMessage(MessageType type, String roomId, String sender, String message){
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
    }
}
