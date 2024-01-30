package kr.toy.lyricsQuizServer.chat.controller;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.chat.service.ChatServiceImpl;
import kr.toy.lyricsQuizServer.common.domain.Response;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class ChatController {


    private final ChatService chatService;

    private final ChatServiceImpl chatServiceImpl;

    @GetMapping("/room")
    @ResponseBody
    public ResponseEntity<Response> enter(@RequestParam Long roomId, @RequestParam(required = false) String password, User user) {
        try {
            chatServiceImpl.enter(roomId, password, user);
            return ResponseEntity.ok().body(new Response(true, null, null, null));
        } catch (IllegalStateException | InvalidDataAccessApiUsageException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Response(false, e.getMessage(), null, null));
        }
    }



    @MessageMapping("/chat/message")
    public void message(ChatMessage message, UserInfo user) {
        chatService.sendMessage(message, user);
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



    


}
