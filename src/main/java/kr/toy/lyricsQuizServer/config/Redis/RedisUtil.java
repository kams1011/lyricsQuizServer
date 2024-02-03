package kr.toy.lyricsQuizServer.config.Redis;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.memory.RedisMemoryGameRoomService;
import kr.toy.lyricsQuizServer.memory.RedisMemoryUserInfoService;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@EnableCaching
@RequiredArgsConstructor
public class RedisUtil {

    //Redis 읽기, 쓰기 등을 여기서 처리.

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisMemoryGameRoomService<GameRoom, Long> memoryGameRoomService;

    private final RedisMemoryUserInfoService<UserInfo, Long> memoryUserInfoService;

    private final HashOperations<String, Long, GameRoom> opsHashGameRoom;

    private final HashOperations<String, Long, UserInfo> opsHashUserInfo;

    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
//    private Map<String, ChannelTopic> topics;

//    @PostConstruct
//    private void init() {
//        topics = new HashMap<>();
//    }

    @Cacheable(value = "USER_INFO", key = "#key")
    public UserInfo findUserInfo(Long key){
        return opsHashUserInfo.get(RedisCategory.USER_INFO.name(), key);
    }

    public void publish(ChatMessage message){
        redisTemplate.convertAndSend(RedisCategory.GAME_ROOM.name(), message);
    }

    public void putGameRoomInRedis(Long id, GameRoom gameRoom){
        memoryGameRoomService.putObject(id, gameRoom, opsHashGameRoom);
    }
    public void putUserInfoInRedis(Long id, UserInfo userInfo){
        memoryUserInfoService.putObject(id, userInfo, opsHashUserInfo);
    }

    public GameRoom getGameRoomFromRedis(Long id){
        return memoryGameRoomService.getObject(id, opsHashGameRoom);
    }

    public UserInfo getUserInfoFromRedis(Long id){
        return memoryUserInfoService.getObject(id, opsHashUserInfo);
    }

}
