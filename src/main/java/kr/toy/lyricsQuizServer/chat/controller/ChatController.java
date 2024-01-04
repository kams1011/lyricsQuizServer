package kr.toy.lyricsQuizServer.chat.controller;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {


    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message, User user) {
        chatService.sendMessage(message, user);
    }


}
