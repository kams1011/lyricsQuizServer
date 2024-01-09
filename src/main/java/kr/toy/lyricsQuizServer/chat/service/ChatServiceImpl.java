package kr.toy.lyricsQuizServer.chat.service;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.controller.port.ChatService;
import kr.toy.lyricsQuizServer.chat.domain.MessageType;
import kr.toy.lyricsQuizServer.chat.domain.TopicType;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, GameRoom> opsHashGameRoom;

    private final ChannelTopic channelTopic;

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

    public ChannelTopic getTopic(Long gameRoomSeq) {

        GameRoom topicName = opsHashGameRoom.get(TopicType.GAME_ROOM.name(), gameRoomSeq);
        return topics.get(topicName);
    }

    @Override
    public void sendMessage(ChatMessage message, User user) {

        message.setSender(user.getNickName());
        // 채팅방 입장시에는 대화명과 메시지를 자동으로 세팅한다.
        if (message.getType().equals(MessageType.ENTER)) {
            message = message.join(user.getNickName());
        }
        // Websocket에 발행된 메시지를 redis로 발행(publish)
        // (채널 이름, 메세지)
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }

    public void temp(Long gameRoomSeq){
       GameRoom gameRoom = opsHashGameRoom.get(TopicType.GAME_ROOM.name(), gameRoomSeq);




    }


    // 1. 이미 다른 방에 접속중인 사용자인지 여부.
    // 1.1 같은 방에 재접속하는 거라면 Redis 저장하지 않음.
    // 2. 방이 가득 찼는지 여부
    // 3. 방이 시작됐는지 여부
    // 4. 비밀방인지 여부
}
