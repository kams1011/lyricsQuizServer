package kr.toy.lyricsQuizServer.memory;

import kr.toy.lyricsQuizServer.memory.Redis.RedisCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisMemoryGameRoomService<GameRoom, Long> implements MemoryService<GameRoom, Long>{

    private final HashOperations<String, Long, GameRoom> gameRoomHashOperations;

    private final String key = RedisCategory.GAME_ROOM.name();

    @Override
    public GameRoom getObject(Long id) {
        return gameRoomHashOperations.get(key, id);
    }

    @Override
    public List<GameRoom> getAll() {
        throw new RuntimeException("잘못된 호출입니다.");
    }

    @Override
    public void putObject(Long id, GameRoom data) {
        gameRoomHashOperations.put(key, id, data);
    }

    @Override
    public void deleteObject(Long id) {
        gameRoomHashOperations.delete(key, id);
    }

}
