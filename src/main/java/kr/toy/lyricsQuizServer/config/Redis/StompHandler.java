package kr.toy.lyricsQuizServer.config.Redis;

import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.chat.service.ChatServiceImpl;
import kr.toy.lyricsQuizServer.config.JwtUtils;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtUtils jwtUtils;

    private final ChatServiceImpl chatService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        //FIXME WebSocketSession TIME OUT 추가
        //FIXME SocketArgumentResolver 에서 JWT를 받지 못할 때 에러 핸들링.
        //FIXME Redis와 Socket Packaging 다시하기
        System.out.println("PRESNED SESSION : " + accessor.getSessionId());
        System.out.println("PRESNED SESSION : " + accessor.getId());
        
        //FIXME 해당 부분 오류 수정, ChatServiceImpl 용도에 맞게 클래스 변경
//        if (StompCommand.CONNECT == accessor.getCommand()) {
//            Map<String, String> attributes = (Map<String, String>)message.getHeaders().get("simpSessionAttributes");
//            String token = attributes.get("token");
//            User user = jwtUtils.getUserBy(token);
//            chatService.putUserInfo(UserInfo.from(user), accessor.getSessionId());
//            //WebSocketSession 이 이미 생성된 상태.
//            //Redis에서 토큰과 WebSocektSession을 매치시켜주자.
//        } else if (StompCommand.SEND == accessor.getCommand()) {
//
//        }

        return message;
    }

}
