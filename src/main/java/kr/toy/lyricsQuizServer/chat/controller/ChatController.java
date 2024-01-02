package kr.toy.lyricsQuizServer.chat.controller;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.domain.ChatRoom;
import kr.toy.lyricsQuizServer.chat.domain.MessageType;
import kr.toy.lyricsQuizServer.chat.service.port.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

    // 채팅방 입장 화면
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable Long gameSeq) {
        return chatRoomRepository.findRoomById(gameSeq);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.JOIN.equals(message.getType()))
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

}
