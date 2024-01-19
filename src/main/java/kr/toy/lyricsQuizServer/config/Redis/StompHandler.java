package kr.toy.lyricsQuizServer.config.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

//    private final JwtTokenProvider jwtTokenProvider;

    // websocket을 통해 들어온 요청이 처리 되기전 실행된다.
    // 닉네임을 그냥 갖고있는게 나을듯.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        accessor.getSessionId();

        // websocket 연결시 헤더의 jwt token 검증
        if (StompCommand.CONNECT == accessor.getCommand()) {
            System.out.println("COOKIE");
            System.out.println(accessor.getFirstNativeHeader("cookie"));
//            jwtTokenProvider.validateToken(accessor.getFirstNativeHeader("token"));
        } else if (StompCommand.BEGIN == accessor.getCommand()) {

        }
        return message;
    }

}
