package kr.toy.lyricsQuizServer.config.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisCacheUtil {


    private final RedisTemplate<String, Object> redisTemplate;


    @Cacheable(value = "userChatDTO", key = "#key")
    public void findUserChatDTO(){

        //FIXME UserChatDTO 를 생성해서 userSeq와 userNickName을 저장하는 걸로 변경.
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

    }




}
