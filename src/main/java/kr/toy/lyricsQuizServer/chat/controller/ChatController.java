package kr.toy.lyricsQuizServer.chat.controller;

import kr.toy.lyricsQuizServer.game.domain.ChatRoom;
import kr.toy.lyricsQuizServer.quiz.domain.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatController {

    private final Map<Long, ChatRoom> chatRooms = new ConcurrentHashMap<>();
    private long roomIdCounter = 1;

    @MessageMapping("/join/{roomId}")
    @SendTo("/topic/chat/{roomId}") //방에 참석하는 API - > ~~님이 참여하셨습니다. 등
    public ChatMessage join(@DestinationVariable Long roomId, ChatMessage message) {
        ChatRoom chatRoom = chatRooms.computeIfAbsent(roomId, id -> new ChatRoom(id));
        chatRoom.join(message.getSender());
        return message;
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}") // 메시지를 보내는 API
    public ChatMessage chat(@DestinationVariable Long roomId, ChatMessage message) {
        // 채팅 메시지 처리 로직
        return message;
    }


    public ChatMessage submit(ChatMessage message){

        return message;
    } // 정답 제출 및 힌트는 Quiz에서 처리하는게 맞을듯.
    // 그러면 chatRooms를 다른곳에서도 쓰게되잖음.




}
