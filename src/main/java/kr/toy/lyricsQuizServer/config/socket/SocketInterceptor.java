package kr.toy.lyricsQuizServer.config.socket;

import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class SocketInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        String accessToken = extractToken(request);
        if (StringUtil.isNullOrEmpty(accessToken)) {
            return false;
        }
        attributes.put("token", accessToken);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        if (exception == null) {

        } else {
            // Handshake가 실패했을 때의 로직
        }
    }

    public String extractToken(ServerHttpRequest request){
        String cookies = request.getHeaders().getFirst(HttpHeaders.COOKIE);
        String regex = "tempAccessTokenName=([^;]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cookies);
        String token = "";
        if (matcher.find()) {
            token = matcher.group(1);
        }
        return token;
    }
}
