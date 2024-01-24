package kr.toy.lyricsQuizServer.chat.service;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.chat.domain.MessageType;
import kr.toy.lyricsQuizServer.config.Redis.RedisCategory;
import kr.toy.lyricsQuizServer.config.Redis.RedisUtil;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
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

    private final HashOperations<String, Long, GameRoom> opsHashGameRoom;

    /** Session 정보에 User 정보를 매핑 **/
    private final HashOperations<String, Long, UserInfo> opsHashUserInfo;

    //단일 ChannelTOpic이 아니라 다중 ChannelTopic을 사용해야함.
    //ChannelTopic 종류도 여러개 생성해야함. -> Enum으로 관리해서 각 채널토픽을 생성하는 메서드를 만들자.

//    public List<GameRoom> findAllRoom() {
//        return opsHashGameRoom.values(TopicType.GAME_ROOM.name());
//    }
//
//    public GameRoom findRoomById(String id) {
//        return opsHashGameRoom.get(TopicType.GAME_ROOM.name(), id);
//    }

//    public ChannelTopic getTopic(Long gameRoomSeq) {
//
//        GameRoom opsHashGameRoom = redisTemplate.opsForHash().get(TopicType.GAME_ROOM.name(), gameRoomSeq);
//    }

    @Override
    public void sendMessage(ChatMessage message) {
        String nickName = message.getSenderNickName();
        message.setSender(nickName);
        // 채팅방 입장시에는 대화명과 메시지를 자동으로 세팅한다.
        if (message.getType().equals(MessageType.ENTER)) {
            message = message.join(nickName);
        }
        // Websocket에 발행된 메시지를 redis로 발행(publish)
        // (채널 이름, 메세지)
        redisUtil.publish(message);
    }

    public void enter(Long gameRoomSeq, String password, User user){
        GameRoom gameRoom = getGameRoom(gameRoomSeq);
        if (getUserInfoBy(user.getUserSeq()).getSessionId() != null) {
            throw new IllegalStateException();
        }  // FIXME 이미 다른 방에 접속중인 사용자인지 여부. -> redis로 체크해야함. 방 접속 여부를.
        if (gameRoom.isRoomOpen(password) && !gameRoom.isEntered(user)) {
            gameRoom.enter(UserInfo.from(user, gameRoomSeq, null));
            createGameRoom(gameRoom);
        } else {
            throw new IllegalStateException(); // 에러 발생
        }
        // SessionId로 저장하는 게 아니라 UserSeq로 저장하고 SessionId를 UserInfo에 넣어야돼
    }

    /**
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
     */
    @Override
    public GameRoom createGameRoom(GameRoom gameRoom) {
        redisUtil.putObject(RedisCategory.GAME_ROOM.name(), gameRoom.getGameRoomSeq(), gameRoom, opsHashGameRoom);
        return gameRoom;
    }

    public void putUserInfo(UserInfo userInfo, Long userSeq){
        redisUtil.putObject(RedisCategory.USER_INFO.name(), userSeq, userInfo, opsHashUserInfo);
    }

    public GameRoom getGameRoom(Long gameRoomSeq){
        GameRoom gameRoom = redisUtil.getObject(RedisCategory.GAME_ROOM.name(), gameRoomSeq, opsHashGameRoom);
        return gameRoom;
    }

    public UserInfo getUserInfoBy(Long userSeq){
        UserInfo userInfo = redisUtil.getObject(RedisCategory.USER_INFO.name(), userSeq, opsHashUserInfo);
        return userInfo;
    }
}