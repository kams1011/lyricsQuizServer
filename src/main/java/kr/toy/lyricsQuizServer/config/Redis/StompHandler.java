package kr.toy.lyricsQuizServer.config.Redis;

import kr.toy.lyricsQuizServer.chat.service.ChatServiceImpl;
import kr.toy.lyricsQuizServer.config.JwtUtils;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtUtils jwtUtils;

    private final ChatServiceImpl chatService;

    private final String destination = "/sub/chat/room/";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        //FIXME WebSocketSession TIME OUT 추가
        //FIXME SocketArgumentResolver 에서 JWT를 받지 못할 때 에러 핸들링.
        //FIXME Redis와 Socket Packaging 다시하기
        //FIXME ChatServiceImpl 용도에 맞게 클래스 변경

        StompCommandHandling(message);

        return message;
    }

    public void StompCommandHandling(Message<?> message){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        try {
            if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
                putUserInfo(accessor, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //FIXME StompHeaderAccessor로 에러 메시지 보낼 수 있는지 확인
        }
    }

    public void putUserInfo(StompHeaderAccessor accessor, Message<?> message){
        Map<String, String> attributes = (Map<String, String>)message.getHeaders().get("simpSessionAttributes");
        String token = attributes.get("token");
        User user = jwtUtils.getUserBy(token);
        Long gameRoomSeq = getGameRoomSeq(accessor);
        chatService.putUserInfo(UserInfo.from(user, gameRoomSeq, accessor.getSessionId()));
    }

    public String parseDestination(StompHeaderAccessor accessor, String key){
        UriComponents components = UriComponentsBuilder.fromUriString(accessor.getDestination()).build();
        String destination = "";
        if (components.getQueryParams().isEmpty()) {
            destination = components.getPathSegments().get(components.getPathSegments().size()-1);
        } else {
            destination = components.getQueryParams().toSingleValueMap().get("roomId");
        }
        return destination;
    }

    public Long getGameRoomSeq(StompHeaderAccessor accessor){
        try {
            return Long.parseLong(parseDestination(accessor, "roomId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }



}
