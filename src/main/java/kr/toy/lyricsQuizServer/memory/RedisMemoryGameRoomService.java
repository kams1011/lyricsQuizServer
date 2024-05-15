package kr.toy.lyricsQuizServer.memory;

import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.memory.Redis.RedisCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisMemoryGameRoomService implements MemoryService<GameRoom, Long>{

    private final HashOperations<String, Long, GameRoom> gameRoomHashOperations;

    private final String key = RedisCategory.GAME_ROOM.name();

    @Override
    public GameRoom getObject(Long id) {
        return gameRoomHashOperations.get(key, id);
    }

    @Override
    public List<GameRoom> getAll() {
        Set<Long> keySet = gameRoomHashOperations.keys(key);
        return gameRoomHashOperations.multiGet(key, keySet);
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
