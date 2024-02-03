package kr.toy.lyricsQuizServer.config.Redis;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.memory.RedisMemoryGameRoomService;
import kr.toy.lyricsQuizServer.memory.RedisMemoryUserInfoService;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
@EnableCaching
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisMemoryGameRoomService<GameRoom, Long> memoryGameRoomService;

    private final RedisMemoryUserInfoService<UserInfo, Long> memoryUserInfoService;

    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
//    private Map<String, ChannelTopic> topics;

//    @PostConstruct
//    private void init() {
//        topics = new HashMap<>();
//    }

    public void publish(ChatMessage message){
        redisTemplate.convertAndSend(RedisCategory.GAME_ROOM.name(), message);
    }

    public void putGameRoomInRedis(Long id, GameRoom gameRoom){
        memoryGameRoomService.putObject(id, gameRoom);
    }

    public void putUserInfoInRedis(Long id, UserInfo userInfo){
        memoryUserInfoService.putObject(id, userInfo);
    }

    @Cacheable(value = "GAME_ROOM", key = "#id")
    public GameRoom getGameRoomFromRedis(Long id){
        return memoryGameRoomService.getObject(id);
    }

    @Cacheable(value = "USER_INFO", key = "#key")
    public UserInfo getUserInfoFromRedis(Long id){
        return memoryUserInfoService.getObject(id);
    }

}
