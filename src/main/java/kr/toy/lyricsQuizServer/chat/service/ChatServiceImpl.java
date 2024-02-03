package kr.toy.lyricsQuizServer.chat.service;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.chat.domain.MessageType;
import kr.toy.lyricsQuizServer.config.Redis.RedisUtil;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final RedisUtil redisUtil;

    private final GameRepository gameRepository;


    //단일 ChannelTOpic이 아니라 다중 ChannelTopic을 사용해야함.
    //ChannelTopic 종류도 여러개 생성해야함. -> Enum으로 관리해서 각 채널토픽을 생성하는 메서드를 만들자.

    @Override
    public void sendMessage(ChatMessage message, UserInfo user) {
        String nickName = user.getNickName();
        message.setSender(nickName);
        if (message.getType().equals(MessageType.ENTER)) {
            message = message.join(nickName);
        }
        redisUtil.publish(message);
    }

    /**
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
     */
    @Override
    public GameRoom createGameRoom(GameRoom gameRoom) {
        redisUtil.putGameRoomInRedis(gameRoom.getGameRoomSeq(), gameRoom);
        return gameRoom;
    }

//    public void publishEntryMessage(User user){
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage = chatMessage.join(user.getNickName());
//        sendMessage(chatMessage, user);
//    }

    public GameRoom getGameRoom(Long gameRoomSeq) {
        GameRoom gameRoom = redisUtil.getGameRoomFromRedis(gameRoomSeq);
        if (gameRoom == null) {
            //FIXME InvalidDataAccessApiUsageException 정리하기.
            gameRoom = GameRoom.from(gameRepository.findById(gameRoomSeq));
            createGameRoom(gameRoom);
        }
        return gameRoom;
    }

    public UserInfo putUserInfo(UserInfo userInfo){
        redisUtil.putUserInfoInRedis(userInfo.getUserSeq(), userInfo);
        return userInfo;
    }

    public UserInfo getUserInfoBy(Long userSeq){
        UserInfo userInfo = redisUtil.getUserInfoFromRedis(userSeq);
        return userInfo;
    }

    public void isHostPresent(GameRoom gameRoom, User host){
        try {
            gameRoom.isUserPresent(host);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("방장이 존재하지 않습니다.");
        }
    }

    public UserInfo findUserInfoOrCreate(User user, Long gameRoomSeq){
        UserInfo userInfo = redisUtil.findUserInfo(user.getUserSeq());

        if (userInfo == null) {
            userInfo = putUserInfo(UserInfo.from(user, gameRoomSeq, null));
        }

        return userInfo;
    }

}
