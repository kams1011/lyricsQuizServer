package kr.toy.lyricsQuizServer.config;

import kr.toy.lyricsQuizServer.chat.service.ChatServiceImpl;
import kr.toy.lyricsQuizServer.config.Redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class SocketInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String cookies = request.getHeaders().getFirst(HttpHeaders.COOKIE);
        String regex = "tempAccessTokenName=([^;]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cookies);


        //FIXME ArgumentResolver 작동되는지 확인하고 Redis에 UserDTO를 넣기.ㅁ
        if (matcher.find()) {
            String accessToken = matcher.group(1);
            attributes.put("token", accessToken);
            return true;
        }

        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

        if (exception == null) {
            // Handshake가 성공적으로 이루어졌을 때의 로직
            // 여기서 넣어주면 될듯.
        } else {
            // Handshake가 실패했을 때의 로직
        }
    }
}
