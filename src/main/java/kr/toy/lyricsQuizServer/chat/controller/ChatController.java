package kr.toy.lyricsQuizServer.chat.controller;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
public class ChatController {


    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message, User user) {
        chatService.sendMessage(message, user);
    }

    public void 검증(){
        
    }

    public void 비밀번호_일치_여부_확인(){
        
    }


    


}
