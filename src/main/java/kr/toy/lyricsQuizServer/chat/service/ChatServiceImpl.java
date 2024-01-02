package kr.toy.lyricsQuizServer.chat.service;

import kr.toy.lyricsQuizServer.chat.domain.ChatRoom;
import kr.toy.lyricsQuizServer.chat.service.port.ChatRoomRepository;
import kr.toy.lyricsQuizServer.config.Redis.RedisSubscriber;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.PostConstruct;
import java.util.*;

@RequiredArgsConstructor
public class ChatServiceImpl {

    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String GAME_ROOM = "GAME_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, GameRoom> opsHashChatRoom;

    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<GameRoom> findAllRoom() {
        return opsHashChatRoom.values(GAME_ROOM);
    }

    public GameRoom findRoomById(String id) {
        return opsHashChatRoom.get(GAME_ROOM, id);
    }

    /**
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
     */
    public ChatRoom createChatRoom(String name) {

        ChatRoom chatRoom = chatRoomRepository.createChatRoom(name);

        // Entity를 새로 만들지 않고 GameEntity에만 저장을 함.
        // 그리고 DTO만 생성해서 핸들링하는게 나을거같음.


//        ChatRoom chatRoom = ChatRoom.builder().c(name);
//        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getChatRoomSeq(), chatRoom);
//        return chatRoom;
        return null;
    }

    /**
     * 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
     */
    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}
