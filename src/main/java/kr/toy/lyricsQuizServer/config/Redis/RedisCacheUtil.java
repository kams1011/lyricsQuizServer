package kr.toy.lyricsQuizServer.config.Redis;

import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisCacheUtil {

    private final RedisTemplate<String, Object> redisTemplate;


    // FIXME 유저 썸네일을 저장할 수도 있으니 opsForValue가 아니라 opForHash로 선언.
    @Cacheable(value = "userInfo", key = "#key")
    public void findUserInfo(){
        HashOperations<String, Long, UserInfo> ops = redisTemplate.opsForHash();


        // 사용자가 채팅방에 입장할 때 userDTO를 저장함.
    }




}
