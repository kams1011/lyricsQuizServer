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

    public void putUserInfo(StompHeaderAccessor accessor, Message<?> message){
        Map<String, String> attributes = (Map<String, String>)message.getHeaders().get("simpSessionAttributes");
        String token = attributes.get("token");
        User user = jwtUtils.getUserBy(token);
        Long gameRoomSeq = getGameRoomSeq(accessor);
        chatService.putUserInfo(UserInfo.from(user, gameRoomSeq, accessor.getSessionId()));
    }

//    public void enterRoom(StompHeaderAccessor accessor){
//        String roomId = parseDestination(accessor,"roomId");
//        UserInfo userInfo = chatService.getUserInfoBy(Long.parseLong(accessor.getUser().getName()));
//        GameRoom gameRoom = chatService.getGameRoom(Long.parseLong(roomId));
//        gameRoom.enter(userInfo);
//        userInfo.enter(Long.parseLong(roomId));
//        chatService.putUserInfo(userInfo, Long.parseLong(accessor.getUser().getName()));
//    }

    public String parseDestination(StompHeaderAccessor accessor, String key){
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(accessor.getDestination());
        Map<String, String> queryParams = builder.build().getQueryParams().toSingleValueMap();
        return queryParams.get(key);
    }

    public Long getGameRoomSeq(StompHeaderAccessor accessor){
        try {
            return Long.parseLong(parseDestination(accessor, "roomId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void StompCommandHandling(Message<?> message){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println(accessor.toString());
        try {
            if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
                putUserInfo(accessor, message);
            }
        } catch (Exception e) {
            e.printStackTrace();

            //FIXME StompHeaderAccessor로 에러 메시지 보낼 수 있는지 확인
        }
    }

}
