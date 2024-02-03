package kr.toy.lyricsQuizServer.memory;

import kr.toy.lyricsQuizServer.config.Redis.RedisCategory;
import kr.toy.lyricsQuizServer.config.Redis.RedisUtil;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.service.port.GameRepository;
import kr.toy.lyricsQuizServer.user.domain.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;

@RequiredArgsConstructor
public class RedisMemoryUserInfoService<UserInfo, Long> implements MemoryService<UserInfo, Long>{

    private final HashOperations<String, Long, UserInfo> opsHashUserInfo;

    private final String key = RedisCategory.USER_INFO.name();

    @Override
    public UserInfo getObject(Long id, HashOperations<String, Long, UserInfo> opsHash) {
        return opsHashUserInfo.get(key, id);
    }

    @Override
    public void putObject(Long id, UserInfo data, HashOperations<String, Long, UserInfo> opsHash) {
        opsHashUserInfo.put(key, id, data);
    }

//    @Override
//    public GameRoom getObject(String category, Long id, HashOperations<String, Long, GameRoom> opsHash) {
//        return opsHash.get(category, id);
//    }
//
//    @Override
//    public void putObject(String category, K id, T data, HashOperations<String, K, T> opsHash) {
//        opsHash.put(category, id, data);
//    }

//    public GameRoom getGameRoomInRedis(Long gameRoomSeq){
//        return getObject(RedisCategory.GAME_ROOM.name(), gameRoomSeq, opsHashGameRoom);
//    }
//
//    public UserInfo getUserInfoInRedis(Long userSeq){
//        return getObject(RedisCategory.GAME_ROOM.name(), userSeq, opsHashUserInfo);
//    }

//
//    @Override
//    public <T, K> T getObject(String category, K id, HashOperations<String, K, T> opsHash) {
//        return opsHash.get(category, id);
//    }
//
//    @Override
//    public <T, K> void putObject(String category, K id, T data, HashOperations<String, K, T> opsHash) {
//        opsHash.put(category, id, data);
//    }
}
