package kr.toy.lyricsQuizServer.memory.Redis;

import kr.toy.lyricsQuizServer.chat.controller.dto.ChatMessage;
import kr.toy.lyricsQuizServer.chat.domain.InvitationInfo;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.controller.response.UserInvitationInfo;
import kr.toy.lyricsQuizServer.game.domain.dto.StreamingInfo;
import kr.toy.lyricsQuizServer.memory.RedisMemoryGameRoomService;
import kr.toy.lyricsQuizServer.memory.RedisMemoryInvitePendingService;
import kr.toy.lyricsQuizServer.memory.RedisMemoryUserInfoService;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@EnableCaching
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    //FIXME MemoryService로 사용할 수 있는 지 한 번 더 확인
    private final RedisMemoryGameRoomService memoryGameRoomService;

    private final RedisMemoryUserInfoService memoryUserInfoService;

    private final RedisMemoryInvitePendingService memoryInvitePendingService;

    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
//    private Map<String, ChannelTopic> topics;

//    @PostConstruct
//    private void init() {
//        topics = new HashMap<>();
//    }

    public void publishMessage(RedisCategory redisCategory, ChatMessage message){
        redisTemplate.convertAndSend(redisCategory.name(), message);
    }

    public void publishStreamingInfo(RedisCategory redisCategory, StreamingInfo streamingInfo){
        redisTemplate.convertAndSend(redisCategory.name(), streamingInfo);
    }

    public void invite(RedisCategory redisCategory, InvitationInfo invitationInfo){
        redisTemplate.convertAndSend(redisCategory.name(), invitationInfo);
    }

    public void putGameRoomInRedis(Long id, GameRoom gameRoom){
        memoryGameRoomService.putObject(id, gameRoom);
    }

    public void putUserInfoInRedis(Long id, UserInfo userInfo){
        memoryUserInfoService.putObject(id, userInfo);
    }
    public void putInvitePendingInfoInRedis(UserInvitationInfo invitationInfo){
        memoryInvitePendingService.putObject(null, invitationInfo);
    }

//    @Cacheable(value = "GAME_ROOM", key = "#id", unless = "#result == null")
    public GameRoom getGameRoomFromRedis(Long id){
        return memoryGameRoomService.getObject(id);
    }

    public List<GameRoom> getAllGameRoomFromRedis(){
        return memoryGameRoomService.getAll();
    }



//    @Cacheable(value = "USER_INFO", key = "#id", unless = "#result == null")
    public UserInfo getUserInfoFromRedis(Long id){
        return memoryUserInfoService.getObject(id);
    }

//    @Cacheable(value = "INVITE_PENDING", key = "#id", unless = "#result == null")
    public Long getInvitePendingInfoFromRedis(UserInvitationInfo userInvitationInfo){
        return (Long)memoryInvitePendingService.getObject(userInvitationInfo);
    }

    public List<UserInvitationInfo> getAllInvitePendingInfoFromRedis(){
        return memoryInvitePendingService.getAll();
    }

    public void deleteGameRoomInRedis(Long id){
        memoryGameRoomService.deleteObject(id);
    }

    public void deleteUserInfoInRedis(Long id){
        memoryUserInfoService.deleteObject(id);
    }

    public void deleteInvitedPendingInfoInRedis(UserInvitationInfo userInvitationInfo){
        memoryInvitePendingService.deleteObject(userInvitationInfo);
    }






}
