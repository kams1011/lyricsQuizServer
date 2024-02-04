package kr.toy.lyricsQuizServer.config.socket;

import kr.toy.lyricsQuizServer.config.JwtUtils;
import kr.toy.lyricsQuizServer.game.controller.port.GameService;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtUtils jwtUtils;

    private final GameService gameService;


    private final String destination = "/sub/chat/room/";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        //FIXME WebSocketSession TIME OUT 추가
        //FIXME SocketArgumentResolver 에서 JWT를 받지 못할 때 에러 핸들링.
        //FIXME Redis와 Socket Packaging 다시하기
        //FIXME ChatServiceImpl 용도에 맞게 클래스 변경
        //FIXME 계속해서 잘못된 접근을 발생시키는 클라이언트 통신 막기.

        try {
            StompCommandHandling(message);
        } catch (IllegalAccessException | IllegalArgumentException | InvalidDataAccessApiUsageException e) {
            //FIXME 어떤 이유로 에러가 발생했는지 클라이언트 단에서 띄워주기.
            //FIXME CONNNECTION을 좀 더 안전한 방법으로 끊기.
            e.printStackTrace();
            throw new RuntimeException(e.getCause().getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }

        return message;
    }

    public void StompCommandHandling(Message<?> message) throws IllegalArgumentException, IllegalAccessException {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            notEnteredUserCheck(accessor, message);
            putUserInfo(accessor, message);
        }
    }

    public void putUserInfo(StompHeaderAccessor accessor, Message<?> message){
        User user = getUserFrom(message);
        Long gameRoomSeq = getGameRoomSeq(accessor);
        gameService.putUserInfo(UserInfo.from(user, gameRoomSeq, accessor.getSessionId()));
    }

    public String parseDestination(StompHeaderAccessor accessor, String key) throws IllegalArgumentException{
        UriComponents components = UriComponentsBuilder.fromUriString(accessor.getDestination()).build();
        String destination = "";
        if (components.getQueryParams().isEmpty()) {
            destination = components.getPathSegments().get(components.getPathSegments().size()-1);
        } else {
            destination = components.getQueryParams().toSingleValueMap().get(key);
        }
        return destination;
    }

    public Long getGameRoomSeq(StompHeaderAccessor accessor) throws IllegalArgumentException {
        try {
            return Long.parseLong(parseDestination(accessor, "roomId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserFrom(Message<?> message){
        Map<String, String> attributes = (Map<String, String>)message.getHeaders().get("simpSessionAttributes");
        String token = attributes.get("token");
        User user = jwtUtils.getUserBy(token);

        return user;
    }

    public void notEnteredUserCheck(StompHeaderAccessor accessor, Message<?> message) throws IllegalAccessException, IllegalArgumentException {
        Long gameRoomSeq = getGameRoomSeq(accessor);
        GameRoom gameRoom = gameService.getGameRoomOrCreate(gameRoomSeq);
        User user = getUserFrom(message);
        UserInfo userInfo = gameService.findUserInfo(user);
        if (gameRoom.isEntered(userInfo)) {
            throw new IllegalAccessException("정상적인 접근이 아닌 유저입니다.");
        }
    }







}
