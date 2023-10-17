package kr.toy.lyricsQuizServer.quiz.controller;

import kr.toy.lyricsQuizServer.quiz.domain.ChatRoom;
import kr.toy.lyricsQuizServer.quiz.domain.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuizController {

    private final Map<Long, ChatRoom> chatRooms = new ConcurrentHashMap<>();
    private long roomIdCounter = 1;

    @MessageMapping("/join/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage join(@DestinationVariable Long roomId, ChatMessage message) {
        ChatRoom chatRoom = chatRooms.computeIfAbsent(roomId, id -> new ChatRoom(id));
        chatRoom.join(message.getSender());
        return message;
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage chat(@DestinationVariable Long roomId, ChatMessage message) {
        // 채팅 메시지 처리 로직
        return message;
    }

    //TODO 로비 리스트 API

    //로비 리스트 API
    //방 제목 검색 API
    //방장 닉네임 검색 API
    //같이할 사람 찾는 API
    //방을 생성하는 API
    //방에 참석하는 API

}
