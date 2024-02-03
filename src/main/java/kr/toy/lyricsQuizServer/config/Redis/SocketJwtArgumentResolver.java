package kr.toy.lyricsQuizServer.config.Redis;

import kr.toy.lyricsQuizServer.config.JwtUtils;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

@RequiredArgsConstructor
public class SocketJwtArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtils jwtUtils;

    private final RedisUtil redisUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception{
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String accessToken = accessor.getSessionAttributes().get("token").toString();

        Long userSeq = jwtUtils.getUserSeqIn(accessToken);
        return redisUtil.getUserInfoFromRedis(userSeq);
    }
}
