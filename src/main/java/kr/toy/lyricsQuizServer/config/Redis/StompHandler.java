package kr.toy.lyricsQuizServer.config.Redis;

import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.chat.service.ChatServiceImpl;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
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

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    // websocket을 통해 들어온 요청이 처리 되기전 실행된다.
    // 닉네임을 그냥 갖고있는게 나을듯.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        //WebSocketSessionID를 받아올 수 있음.
        //WebSocketSessionID와 닉네임을 매핑하면 될듯.
        //FIXME WebSocketSession TIME OUT 추가
        //FIXME SocketArgumentResolver 에서 JWT를 받지 못할 때 에러 핸들링.
        //FIXME Redis와 Socket Packaging 다시하기
        if (StompCommand.CONNECT == accessor.getCommand()) {

        } else if (StompCommand.SEND == accessor.getCommand()) {

        }

        return message;
    }

}
