package kr.toy.lyricsQuizServer.chat.service;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.chat.domain.MessageType;
import kr.toy.lyricsQuizServer.config.Redis.RedisCategory;
import kr.toy.lyricsQuizServer.config.Redis.RedisUtil;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final RedisUtil redisUtil;

    private final GameRepository gameRepository;

    private final HashOperations<String, Long, GameRoom> opsHashGameRoom;

    /** Session 정보에 User 정보를 매핑 **/
    private final HashOperations<String, Long, UserInfo> opsHashUserInfo;

    //단일 ChannelTOpic이 아니라 다중 ChannelTopic을 사용해야함.
    //ChannelTopic 종류도 여러개 생성해야함. -> Enum으로 관리해서 각 채널토픽을 생성하는 메서드를 만들자.

    @Override
    public void sendMessage(ChatMessage message, User user) {
        String nickName = user.getNickName();
        message.setSender(nickName);
        if (message.getType().equals(MessageType.ENTER)) {
            message = message.join(nickName);
        }
        redisUtil.publish(message);
    }

    public void enter(Long gameRoomSeq, String password, User user){
        GameRoom gameRoom = getGameRoom(gameRoomSeq);
        UserInfo userInfo = findUserInfoOrCreate(user, gameRoomSeq);
        if (isRoomEnterAllowed(gameRoom, password, user, userInfo)) {
            gameRoom.enter(userInfo);
            createGameRoom(gameRoom);
        } else {
            throw new IllegalStateException("방 입장이 불가능합니다.");
        }
    }

    /**
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
     */
    @Override
    public GameRoom createGameRoom(GameRoom gameRoom) {
        redisUtil.putObject(RedisCategory.GAME_ROOM.name(), gameRoom.getGameRoomSeq(), gameRoom, opsHashGameRoom);
        return gameRoom;
    }

//    public void publishEntryMessage(User user){
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage = chatMessage.join(user.getNickName());
//        sendMessage(chatMessage, user);
//    }

    public UserInfo putUserInfo(UserInfo userInfo){
        redisUtil.putObject(RedisCategory.USER_INFO.name(), userInfo.getUserSeq(), userInfo, opsHashUserInfo);
        return userInfo;
    }

    public GameRoom getGameRoom(Long gameRoomSeq){
        GameRoom gameRoom = redisUtil.getObject(RedisCategory.GAME_ROOM.name(), gameRoomSeq, opsHashGameRoom);
        if (gameRoom == null) {
            gameRoom = GameRoom.from(gameRepository.findById(gameRoomSeq));
            createGameRoom(gameRoom);
        }
        return gameRoom;
    }

    public UserInfo getUserInfoBy(Long userSeq){
        UserInfo userInfo = redisUtil.getObject(RedisCategory.USER_INFO.name(), userSeq, opsHashUserInfo);
        return userInfo;
    }

    public boolean isRoomEnterAllowed(GameRoom gameRoom, String password, User user, UserInfo userInfo){
        return gameRoom.isRoomOpen(password) && !gameRoom.isEntered(user) && !userInfo.inGame();
    }

    public UserInfo findUserInfoOrCreate(User user, Long gameRoomSeq){
        UserInfo userInfo = getUserInfoBy(user.getUserSeq());

        if (userInfo == null) {
            userInfo = putUserInfo(UserInfo.from(user, gameRoomSeq, null));
        }

        return userInfo;
    }


}
