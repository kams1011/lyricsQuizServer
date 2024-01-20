package kr.toy.lyricsQuizServer.config.Redis;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    //Redis 읽기, 쓰기 등을 여기서 처리.

    private final RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, Long, GameRoom> opsHashGameRoom;

    private HashOperations<String, Long, UserInfo> opsHashUserInfo;


    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashGameRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }


    // FIXME 유저 썸네일을 저장할 수도 있으니 opsForValue가 아니라 opForHash로 선언.
    @Cacheable(value = "userInfo", key = "#key")
    public void findUserInfo(){
        HashOperations<String, Long, UserInfo> ops = redisTemplate.opsForHash();


        // 사용자가 채팅방에 입장할 때 userDTO를 저장함.
    }


    public void put(User user){
        opsHashUserInfo.put(RedisCategory.USER_INFO.name(), user.getUserSeq(), UserInfo.from(user));
    }

    public void put(GameRoom gameRoom){
        opsHashGameRoom.put(RedisCategory.GAME_ROOM.name(), gameRoom.getGameRoomSeq(), gameRoom);
    }

    public void publish(ChatMessage message){
        redisTemplate.convertAndSend(message.getRoomId(), message);
    }

    public <T> T getObject(String category, Long seq, HashOperations<String, Long, T> opsHash) {
        return opsHash.get(category, seq);
    }

    public <T> void putObject(String category, Long seq, T data, HashOperations<String, Long, T> opsHash) {
        opsHash.put(category, seq, data);
    }

    public GameRoom getGameRoom(Long gameRoomSeq){
        return opsHashGameRoom.get(RedisCategory.GAME_ROOM.name(), gameRoomSeq);
    }

    public UserInfo getUserInfo(Long userSeq){
        return opsHashUserInfo.get(RedisCategory.USER_INFO.name(), userSeq);
    }







}
