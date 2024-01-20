package kr.toy.lyricsQuizServer.chat.service;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.chat.domain.MessageType;
import kr.toy.lyricsQuizServer.chat.domain.TopicType;
import kr.toy.lyricsQuizServer.common.domain.Response;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, Long, GameRoom> opsHashGameRoom;

    private HashOperations<String, Long, UserInfo> opsHashUserInfo;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    //단일 ChannelTOpic이 아니라 다중 ChannelTopic을 사용해야함.
    //ChannelTopic 종류도 여러개 생성해야함. -> Enum으로 관리해서 각 채널토픽을 생성하는 메서드를 만들자.

    @PostConstruct
    private void init() {
        opsHashGameRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

//    public List<GameRoom> findAllRoom() {
//
//        return opsHashGameRoom.values(TopicType.GAME_ROOM.name());
//    }
//
//    public GameRoom findRoomById(String id) {
//        return opsHashGameRoom.get(TopicType.GAME_ROOM.name(), id);
//    }

    /**
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
     */

    @Override
    public GameRoom create(GameRoom gameRoom) {
        opsHashGameRoom.put(TopicType.GAME_ROOM.name(), gameRoom.getGameRoomSeq(), gameRoom);
        return gameRoom;
    }

//    public ChannelTopic getTopic(Long gameRoomSeq) {
//
//        GameRoom opsHashGameRoom = redisTemplate.opsForHash().get(TopicType.GAME_ROOM.name(), gameRoomSeq);
//    }

    @Override
    public void sendMessage(ChatMessage message) {
        //FIXME Channel 변경
//        String nickName = user.getNickName();
        String nickName = message.getSenderNickName();

        message.setSender(nickName);
        // 채팅방 입장시에는 대화명과 메시지를 자동으로 세팅한다.
        if (message.getType().equals(MessageType.ENTER)) {
            message = message.join(nickName);
        }
        // Websocket에 발행된 메시지를 redis로 발행(publish)
        // (채널 이름, 메세지)
//        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
        redisTemplate.convertAndSend("2", message);
    }

    public void enter(Long gameRoomSeq, String password, User user){
       GameRoom gameRoom = opsHashGameRoom.get(TopicType.GAME_ROOM.name(), gameRoomSeq);
       if (gameRoom.isRoomOpen(password) && !gameRoom.isEntered(user)) {
           temp2(gameRoom, user);
           temp(user);
       } else {
           throw new IllegalStateException(); // 에러 발생
       }
        // FIXME 이미 다른 방에 접속중인 사용자인지 여부. -> redis로 체크해야함. 방 접속 여부를.
    }



    // FIXME 이하 두 메서드 RedisUtil로 이동.
    public void temp(User user){
        UserInfo userInfo = UserInfo.from(user);
        opsHashUserInfo.put("USER_INFO", user.getUserSeq(), userInfo);
    }

    public void temp2(GameRoom gameRoom, User user){
        gameRoom.enter(user);
        opsHashGameRoom.put(TopicType.GAME_ROOM.name(), gameRoom.getGameRoomSeq(), gameRoom);
    }





}
