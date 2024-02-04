package kr.toy.lyricsQuizServer.memory;

import kr.toy.lyricsQuizServer.memory.Redis.RedisCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisMemoryUserInfoService<UserInfo, Long> implements MemoryService<UserInfo, Long>{

    private final HashOperations<String, Long, UserInfo> userInfoHashOperations;

    private final String key = RedisCategory.USER_INFO.name();

    @Override
    public UserInfo getObject(Long id) {
        return userInfoHashOperations.get(key, id);
    }

    @Override
    public void putObject(Long id, UserInfo data) {
        userInfoHashOperations.put(key, id, data);
    }

    @Override
    public void deleteObject(Long id) {
        userInfoHashOperations.delete(key, id);
    }

}
