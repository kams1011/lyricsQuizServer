package kr.toy.lyricsQuizServer.chat.service;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.chat.domain.MessageType;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.memory.Redis.RedisCategory;
import kr.toy.lyricsQuizServer.memory.Redis.RedisUtil;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final RedisUtil redisUtil;

    //단일 ChannelTOpic이 아니라 다중 ChannelTopic을 사용해야함.
    //ChannelTopic 종류도 여러개 생성해야함. -> Enum으로 관리해서 각 채널토픽을 생성하는 메서드를 만들자.

    @Override
    public void sendMessage(ChatMessage message, UserInfo user) {
        String nickName = user.getNickName();
        message.setSender(nickName);
        GameRoom gameRoom = redisUtil.getGameRoomFromRedis(Long.parseLong(message.getRoomId()));

        System.out.println("check하는부분");
        System.out.println(gameRoom.getUserList().size());
        System.out.println(gameRoom.isUserPresent(user));

        if (message.getType().equals(MessageType.ENTER) && !gameRoom.isUserPresent(user)) {
            message = message.join(nickName);
        }
        redisUtil.publish(RedisCategory.GAME_ROOM, message);
    }

    @Override
    public void invite(ChatMessage message, UserInfo user){

    }


    @Override
    public void exit() { // Session을 제거해주기.

    }


}
