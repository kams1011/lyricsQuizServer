package kr.toy.lyricsQuizServer.chat.controller;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class ChatController {


    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message, User user) {
        System.out.println("hihi");
        System.out.println(user.getUserSeq());
        chatService.sendMessage(message);
    }

    @MessageMapping("/game/start")
    public void streaming(Long gameSeq){
        // 게임 시작 할 때 검증해야하는 것??
        // 방이 존재하는가. 방장이 존재하는가. Default 검증 ( 메시지 보낼 떄도 적용)
        // 방이 존재하는가. 방이 준비단계인가. 방장이 존재하는가 ( 이중 검증 ) -> 입장 시와 공통 검증 로직.
        // 방장 이외에 인원이 존재하는가. 시작 버튼을 누르는게 방장인가.> 시작 시 검증
        // 준비완료 상태를 보내야될듯? 방장 이외의 인원이 전부 준비완료 상태인가. -> 추가 기능.
    }

    public void 검증(){
        
    }

    public void 비밀번호_일치_여부_확인(){
        
    }


    


}
